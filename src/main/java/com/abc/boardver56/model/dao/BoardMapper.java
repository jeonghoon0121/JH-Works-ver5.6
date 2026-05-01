package com.abc.boardver56.model.dao;

import com.abc.boardver56.model.dto.BoardDTO;
import com.abc.boardver56.model.dto.CommentDTO;
import com.abc.boardver56.model.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BoardMapper {
    // ===== Board =====
    List<BoardDTO> findAllBoards();
    BoardDTO findOneBoard(@Param("boardId") int boardId);

    void addBoard(BoardDTO boardDTO);
    void updateBoard(BoardDTO boardDTO);
    void deleteBoard(BoardDTO boardDTO);

    // ✅ 페이징된 게시판 조회
    List<BoardDTO> findBoardsPaged(@Param("offset") int offset,
                                   @Param("limit") int limit);

    // ✅ 총 게시판 개수 조회
    int countBoards();

    // ===== Post =====
    List<PostDTO> findAllPosts();
    PostDTO findOnePost(@Param("postId") int postId);

    void addPost(PostDTO postDTO);
    void updatePost(PostDTO postDTO);
    void deletePost(PostDTO postDTO);

    // ✅ 페이징된 게시글 조회
    List<PostDTO> findPostsByBoardId(@Param("boardId") int boardId,
                                     @Param("offset") int offset,
                                     @Param("limit") int limit);

    // ✅ 총 글 개수 조회
    int countPostsByBoardId(@Param("boardId") int boardId);

    // ===== Comment =====
    List<CommentDTO> findAllComments();
    CommentDTO findOneComment(@Param("commentId") int commentId);

    void addComment(CommentDTO commentDTO);
    void updateComment(CommentDTO commentDTO);
    void deleteComment(CommentDTO commentDTO);

    void increaseViewCount(@Param("postId") int postId);

    // ===== Post 검색 =====
    List<PostDTO> searchPosts(@Param("boardId") int boardId,
                              @Param("keyword") String keyword);

    //oid게시물만 검색할 경우 사용 boardver5.6
    List<PostDTO> findByOld(Boolean old);
}
