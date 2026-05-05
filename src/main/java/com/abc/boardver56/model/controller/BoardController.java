package com.abc.boardver56.model.controller;

import com.abc.boardver56.model.dto.BoardDTO;
import com.abc.boardver56.model.dto.CommentDTO;
import com.abc.boardver56.model.dto.PostDTO;
import com.abc.boardver56.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping({"", "/", "/home", "/home/"})
    public String BoardHome(Model model) {
        return "index";
    }

    @GetMapping({"/board"})
    public String mBoardHome(Model model) {
        return "board/boardList";
    }

    @GetMapping("/board/{boardId}")
    public String getBoardDetail(@PathVariable int boardId,
                                 @RequestParam(defaultValue = "1") int page,
                                 Model model) {
        BoardDTO board = boardService.findOneBoard(boardId);

        int size = 10;
        List<PostDTO> posts = boardService.findPostsByBoardId(boardId, page, size);
        int totalPages = boardService.getTotalPages(boardId, size);

        model.addAttribute("board", board);
        model.addAttribute("postList", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "board/boardDetail";
    }

    // ===== Board 관리 =====
    @GetMapping("/board/add")
    public String showAddBoardForm(Model model) {
        model.addAttribute("board", new BoardDTO());
        return "board/boardAddForm";
    }

    @PostMapping("/board/add")
    public String createBoard(@ModelAttribute BoardDTO board) {
        boardService.addBoard(board);
        return "redirect:/board/" + board.getBoardId();
    }

    @GetMapping("/board/update")
    public String showUpdateBoardForm(Model model) {
        model.addAttribute("board", new BoardDTO());
        return "board/boardUpdateForm";
    }

    @PostMapping("/board/update")
    public String updateBoard(@ModelAttribute BoardDTO board) {
        boardService.updateBoard(board);
        return "redirect:/board/" + board.getBoardId();
    }

    @GetMapping("/board/delete")
    public String showDeleteBoardForm(Model model) {
        model.addAttribute("board", new BoardDTO());
        return "board/boardDeleteForm";
    }

    @PostMapping("/board/delete")
    public String deleteBoard(@ModelAttribute BoardDTO board) {
        boardService.deleteBoard(board);
        return "redirect:/home";
    }

    // ===== Post 관리 =====
    @GetMapping("/post")
    public String getPostList(Model model) {
        model.addAttribute("postList", boardService.findAllPosts());
        return "post/postList";
    }

    @GetMapping("/post/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new PostDTO());
        return "post/postAddForm";
    }

    @PostMapping("/post/add")
    public String createPost(@ModelAttribute PostDTO postDTO, HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        boardService.addPostWithFile(postDTO, clientIp);
        return "redirect:/board/" + postDTO.getBoardId();
    }

    @GetMapping("/post/update")
    public String showUpdatePostForm(@ModelAttribute("post") PostDTO postDTO, Model model) {
        if (postDTO.getPostId() != null && postDTO.getPostId() != 0) {
            PostDTO realPost = boardService.findOnePost(postDTO.getPostId());
            model.addAttribute("post", realPost);
        } else {
            model.addAttribute("post", new PostDTO());
        }
        return "post/postUpdateForm";
    }

    @PostMapping("/post/update")
    public String updatePost(@ModelAttribute PostDTO postDTO) {
        boardService.updatePostWithFile(postDTO);
        return "redirect:/post/" + postDTO.getPostId();
    }

    @GetMapping("/post/delete")
    public String deletePost(@ModelAttribute("post") PostDTO postDTO, Model model) {
        if (postDTO.getPostId() != null && postDTO.getPostId() != 0) {
            PostDTO realPost = boardService.findOnePost(postDTO.getPostId());
            model.addAttribute("post", realPost);
        } else {
            model.addAttribute("post", new PostDTO());
        }
        return "post/postDeleteForm";
    }

    @PostMapping("/post/delete")
    public String deletePostConfirm(@ModelAttribute PostDTO postDTO) {
        boardService.deletePost(postDTO);
        Integer boardId = postDTO.getBoardId();
        if (boardId == null || boardId == 0) {
            return "redirect:/board/1";
        }
        return "redirect:/board/" + boardId;
    }

    // ===== Comment 관리 (게시글 상세 보기) =====
    @GetMapping("/post/{postId}")
    public String getPostDetail(@PathVariable int postId, Model model) {
        boardService.increaseViewCount(postId);
        PostDTO post = boardService.findOnePost(postId);

        BoardDTO board = boardService.findOneBoard(post.getBoardId());
        model.addAttribute("board", board);

        List<CommentDTO> comments = boardService.findCommentsByPostId(postId);

        model.addAttribute("post", post);
        model.addAttribute("commentList", comments);

        CommentDTO comment = new CommentDTO();
        comment.setPostId(postId);
        model.addAttribute("comment", comment);

        return "post/postDetail";
    }

    @PostMapping("/comment/add")
    public String createComment(@ModelAttribute CommentDTO commentDTO) {
        boardService.addComment(commentDTO);
        return "redirect:/post/" + commentDTO.getPostId();
    }

    @PostMapping("/comment/update")
    public String updateComment(@ModelAttribute CommentDTO commentDTO) {
        boardService.updateComment(commentDTO);
        return "redirect:/post/" + commentDTO.getPostId();
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@ModelAttribute CommentDTO commentDTO) {
        boardService.deleteComment(commentDTO);
        return "redirect:/post/" + commentDTO.getPostId();
    }

    @GetMapping("/board/{boardId}/search")
    public String searchPosts(@PathVariable int boardId,
                              @RequestParam String keyword,
                              Model model) {
        BoardDTO board = boardService.findOneBoard(boardId);
        List<PostDTO> posts = boardService.searchPosts(boardId, keyword);

        model.addAttribute("board", board);
        model.addAttribute("postList", posts);
        model.addAttribute("keyword", keyword);

        return "board/boardDetail";
    }
}
