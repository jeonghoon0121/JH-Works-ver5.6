package com.abc.boardver56.model.controller;

import com.abc.boardver56.model.dto.BoardDTO;
import com.abc.boardver56.model.dto.CommentDTO;
import com.abc.boardver56.model.dto.PostDTO;
import com.abc.boardver56.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<BoardDTO> boardDTOS = boardService.findAllBoards();
        model.addAttribute("boardList", boardDTOS);
        return "index";
    }
    @GetMapping({"/board"})
    public String mBoardHome(Model model) {
        List<BoardDTO> boardDTOS = boardService.findAllBoards();
        model.addAttribute("boardList", boardDTOS);
        return "board/boardList";
    }




    @GetMapping("/board/{boardId}")
    public String getBoardDetail(@PathVariable int boardId,
                                 @RequestParam(defaultValue = "1") int page,
                                 Model model) {
        List<BoardDTO> boardDTOS = boardService.findAllBoards();
        model.addAttribute("boardList", boardDTOS);

        BoardDTO board = boardService.findOneBoard(boardId);

        int size = 10; // 한 페이지에 보여줄 글 수
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
        model.addAttribute("boardList", boardService.findAllBoards());
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
        model.addAttribute("boardList", boardService.findAllBoards());
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
        model.addAttribute("boardList", boardService.findAllBoards());
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
        model.addAttribute("boardList", boardService.findAllBoards());
        model.addAttribute("postList", boardService.findAllPosts());
        return "board/postList";
    }

    @GetMapping("/post/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("boardList", boardService.findAllBoards());
        model.addAttribute("post", new PostDTO());
        return "board/postAddForm";
    }

    @PostMapping("/post/add")
    public String createPost(@ModelAttribute PostDTO postDTO) {
//        boardService.addPost(postDTO);
        boardService.addPostWithFile(postDTO); // 파일 저장 + DB 저장
        return "redirect:/board/" + postDTO.getBoardId();
    }

    @GetMapping("/post/update")
    public String showUpdatePostForm(Model model) {
        model.addAttribute("boardList", boardService.findAllBoards());
        model.addAttribute("post", new PostDTO());
        return "board/postUpdateForm";
    }

    @PostMapping("/post/update")
    public String updatePost(@ModelAttribute PostDTO postDTO) {
        boardService.updatePost(postDTO);
        return "redirect:/board/" + postDTO.getBoardId();
    }

    @GetMapping("/post/delete")
    public String showDeletePostForm(Model model) {
        model.addAttribute("boardList", boardService.findAllBoards());
        model.addAttribute("post", new PostDTO());
        return "board/postDeleteForm";
    }

    @PostMapping("/post/delete")
    public String deletePostByPostId(@ModelAttribute PostDTO postDTO) {
        Integer boardId = boardService.findBoardIdByPostId(postDTO.getPostId());
        boardService.deletePost(postDTO);
        return "redirect:/board/" + boardId;
    }

    // ===== Comment 관리 =====
    // ===== Comment 관리 (게시글 상세 보기) =====
    @GetMapping("/post/{postId}")
    public String getPostDetail(@PathVariable int postId, Model model) {
        model.addAttribute("boardList", boardService.findAllBoards());

        // 1. 조회수 증가
        boardService.increaseViewCount(postId);

        // 2. 게시글 데이터 가져오기
        PostDTO post = boardService.findOnePost(postId);

        // ✅ 3. [추가] 게시판 데이터 가져오기 (에러 해결 핵심!)
        // post 객체에 담긴 boardId를 사용해서 해당 게시판 정보를 가져옵니다.
        BoardDTO board = boardService.findOneBoard(post.getBoardId());
        model.addAttribute("board", board); // 이제 템플릿의 ${board.name}이 작동합니다.

        // 4. 댓글 리스트 가져오기
        List<CommentDTO> comments = boardService.findCommentsByPostId(postId);

        model.addAttribute("post", post);
        model.addAttribute("commentList", comments);

        // 5. 댓글 입력을 위한 빈 객체
        CommentDTO comment = new CommentDTO();
        comment.setPostId(postId);
        model.addAttribute("comment", comment);

        return "board/postDetail";
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
        List<BoardDTO> boardDTOS = boardService.findAllBoards();
        model.addAttribute("boardList", boardDTOS);

        BoardDTO board = boardService.findOneBoard(boardId);
        List<PostDTO> posts = boardService.searchPosts(boardId, keyword);

        model.addAttribute("board", board);
        model.addAttribute("postList", posts);
        model.addAttribute("keyword", keyword);

        return "board/boardDetail";
    }
}
