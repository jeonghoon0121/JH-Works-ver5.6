package com.abc.boardver56.service;

import com.abc.boardver56.model.dao.BoardMapper;
import com.abc.boardver56.model.dto.BoardDTO;
import com.abc.boardver56.model.dto.CommentDTO;
import com.abc.boardver56.model.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deletePost(PostDTO postDTO) {
        PostDTO existingPost = mapper.findOnePost(postDTO.getPostId());
        if (existingPost == null) {
            throw new RuntimeException("Post not found");
        }
        if (!existingPost.getAuthorId().equals(postDTO.getAuthorId()) ||
                !existingPost.getPassword().equals(postDTO.getPassword())) {
            throw new RuntimeException("Invalid authorId or password");
        }
        mapper.deletePost(postDTO);
    }

    @Transactional
    public Integer findBoardIdByPostId(Integer postId) {
        PostDTO post = mapper.findOnePost(postId);
        if (post == null) {
            throw new RuntimeException("Post not found");
        }
        return post.getBoardId();
    }

    @Transactional
    public void addPostWithFile(PostDTO postDTO) {
        if (postDTO.getFile() != null && !postDTO.getFile().isEmpty()) {
            String fileName = java.util.UUID.randomUUID() + "_" + postDTO.getFile().getOriginalFilename();
            java.nio.file.Path uploadDir = java.nio.file.Paths.get("uploads");
            java.nio.file.Path path = uploadDir.resolve(fileName);

            try {
                if (!java.nio.file.Files.exists(uploadDir)) {
                    java.nio.file.Files.createDirectories(uploadDir);
                }
                java.nio.file.Files.copy(postDTO.getFile().getInputStream(), path,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                postDTO.setAttachmentUrl("/uploads/" + fileName);
            } catch (java.io.IOException e) {
                throw new RuntimeException("파일 저장 실패", e);
            }
        }
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