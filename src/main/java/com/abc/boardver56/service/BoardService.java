package com.abc.boardver56.service;

import com.abc.boardver56.model.dao.BoardMapper;
import com.abc.boardver56.model.dto.BoardDTO;
import com.abc.boardver56.model.dto.CommentDTO;
import com.abc.boardver56.model.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // final이 붙은 mapper를 자동으로 생성자 주입해줍니다.
public class BoardService {

    private final BoardMapper mapper;

    // ===== Board =====
    public List<BoardDTO> findAllBoards() {
        return mapper.findAllBoards();
    }

    public BoardDTO findOneBoard(int boardId) {
        return mapper.findOneBoard(boardId);
    }

    @Transactional
    public void addBoard(BoardDTO boardDTO) {
        mapper.addBoard(boardDTO);
    }

    @Transactional
    public void updateBoard(BoardDTO boardDTO) {
        mapper.updateBoard(boardDTO);
    }

    @Transactional
    public void deleteBoard(BoardDTO boardDTO) {
        mapper.deleteBoard(boardDTO);
    }

    // ===== Post =====
    public List<PostDTO> findAllPosts() {
        return mapper.findAllPosts();
    }

    public PostDTO findOnePost(int postId) {
        return mapper.findOnePost(postId);
    }

    // ✅ 페이징된 게시글 조회
    public List<PostDTO> findPostsByBoardId(int boardId, int page, int size) {
        int offset = (page - 1) * size;
        return mapper.findPostsByBoardId(boardId, offset, size);
    }

    // ✅ 총 글 페이지 수 계산
    public int getTotalPages(int boardId, int size) {
        int totalCount = mapper.countPostsByBoardId(boardId);
        return (int) Math.ceil((double) totalCount / size);
    }

    @Transactional
    public void addPost(PostDTO postDTO) {
        mapper.addPost(postDTO);
    }

    @Transactional
    public void updatePost(PostDTO postDTO) {
        postDTO.setUpdatedAt(java.time.LocalDateTime.now());
        mapper.updatePost(postDTO);
    }


    @Transactional
    public Integer findBoardIdByPostId(Integer postId) {
        PostDTO post = mapper.findOnePost(postId);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        return post.getBoardId();
    }
    private String saveFile(MultipartFile file) {
        // 1. 파일이 없으면 저장하지 않고 null 반환
        if (file == null || file.isEmpty()) {
            return null;
        }

        // 2. 파일명 중복 방지를 위한 UUID 생성
        String fileName = java.util.UUID.randomUUID() + "_" + file.getOriginalFilename();
        java.nio.file.Path uploadDir = java.nio.file.Paths.get("uploads");
        java.nio.file.Path path = uploadDir.resolve(fileName);

        try {
            // 3. 디렉토리가 없으면 생성
            if (!java.nio.file.Files.exists(uploadDir)) {
                java.nio.file.Files.createDirectories(uploadDir);
            }
            // 4. 파일 복사 저장
            java.nio.file.Files.copy(file.getInputStream(), path,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // 5. DB에 저장할 상대 경로 반환
            return "/uploads/" + fileName;
        } catch (java.io.IOException e) {
            throw new RuntimeException("파일 물리적 저장 실패", e);
        }
    }
    @Transactional
    public void updatePostWithFile(PostDTO postDTO) {
        // 1. DB에서 기존 게시글 정보 조회
        PostDTO existingPost = mapper.findOnePost(postDTO.getPostId());

        if (existingPost == null) {
            throw new RuntimeException("해당 게시글이 존재하지 않습니다.");
        }

        // 2. 비밀번호 비교
        if (!existingPost.getPassword().equals(postDTO.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않아 수정할 수 없습니다.");
        }

        // 3. 새 파일이 업로드되었는지 확인 후 저장
        String newAttachmentUrl = saveFile(postDTO.getFile());
        if (newAttachmentUrl != null) {
            postDTO.setAttachmentUrl(newAttachmentUrl);
        }

        // 4. 매퍼 호출
        mapper.updatePost(postDTO);
    }

    @Transactional
    public void deletePost(PostDTO postDTO) {
        // 1. 권한 검증
        PostDTO existingPost = mapper.findOnePost(postDTO.getPostId());
        if (existingPost == null) {
            throw new RuntimeException("게시글이 존재하지 않습니다.");
        }
        if (!existingPost.getPassword().equals(postDTO.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 2. 삭제용 DTO 생성 및 필드 세팅 (소프트 삭제)
        PostDTO deleteData = new PostDTO();
        deleteData.setPostId(postDTO.getPostId());
        deleteData.setStatus("DELETED");

        // 3. 매퍼 호출
        mapper.softDeletePost(deleteData);
    }

    @Transactional
    public void addPostWithFile(PostDTO postDTO, String clientIp) {
        // 1. 파일 저장 처리
        String attachmentUrl = saveFile(postDTO.getFile());
        if (attachmentUrl != null) {
            postDTO.setAttachmentUrl(attachmentUrl);
        }

        // 2. 기타 정보 및 기본값 설정
        postDTO.setIpHash(clientIp);

        if (postDTO.getStatus() == null) postDTO.setStatus("PUBLISHED");
        if (postDTO.getNotice() == null) postDTO.setNotice(false);
        if (postDTO.getSecret() == null) postDTO.setSecret(false);
        if (postDTO.getThumbnailUrl() == null) postDTO.setThumbnailUrl("");
        if (postDTO.getAccessLevel() == null) {
            postDTO.setAccessLevel("0");
        }

        // 3. 매퍼를 통한 DB 저장
        mapper.addPost(postDTO);
    }

    // ===== Comment =====
    public List<CommentDTO> findAllComments() {
        return mapper.findAllComments();
    }

    public CommentDTO findOneComment(int commentId) {
        return mapper.findOneComment(commentId);
    }

    public List<CommentDTO> findCommentsByPostId(int postId) {
        List<CommentDTO> result = new ArrayList<>();
        for (CommentDTO comment : findAllComments()) {
            if (comment.getPostId() == postId) {
                result.add(comment);
            }
        }
        return result;
    }

    @Transactional
    public void addComment(CommentDTO commentDTO) {
        mapper.addComment(commentDTO);
    }

    @Transactional
    public void updateComment(CommentDTO commentDTO) {
        mapper.updateComment(commentDTO);
    }

    @Transactional
    public void deleteComment(CommentDTO commentDTO) {
        mapper.deleteComment(commentDTO);
    }

    public void increaseViewCount(int postId) {
        mapper.increaseViewCount(postId);
    }

    public List<PostDTO> searchPosts(int boardId, String keyword) {
        return mapper.searchPosts(boardId, keyword);
    }


}