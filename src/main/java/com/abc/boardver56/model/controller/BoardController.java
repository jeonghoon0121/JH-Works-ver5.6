package com.abc.boardver56.model.controller;

import com.abc.boardver56.model.dto.BoardDTO;
import com.abc.boardver56.model.dto.CommentDTO;
import com.abc.boardver56.model.dto.PostDTO;
import com.abc.boardver56.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class BoardController {
    // 1. 사용할 서비스 객체를 선언 (상태를 변경할 수 없도록 final 권장)
    private final BoardService boardService;

    // 2. 생성자를 직접 작성 (이것이 의존성 주입의 핵심!)
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 1. 메인 홈
    @GetMapping({"","/", "/index", "/home"}) // 여러 경로를 중괄호로 묶음
    public String home(Model model) {
        // 1. 서비스에서 게시판 목록을 가져옵니다. (sort_order 순 정렬됨)
        List<BoardDTO> boards = boardService.findAllBoards();

        // 2. "boardList"라는 이름으로 데이터를 모델에 담습니다.
        model.addAttribute("boardList", boards);

        // 3. index.html을 보여줍니다.
        return "index";
    }

    // 2. 게시판 목록
    @GetMapping("/board")
    public String boardMain() {
        return "board/boardList";
    }
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

    // 3. 전체 포스트 목록 (사이드바 '전체 포스트' 클릭 시)
    @GetMapping("/post")
    public String listPosts(HttpServletRequest request, Model model) {
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("postList", boardService.findAllPosts());
        return "index";
    }
    // --- 여기서부터 순서가 중요합니다! ---

    // [추가] 5-1. 게시글 작성 폼 (GET)
    // 반드시 {postId} 매핑보다 위에 있어야 "add"를 숫자로 오해하지 않습니다.
    @GetMapping("/post/add")
    public String addForm(Model model) {
        model.addAttribute("post", new PostDTO());
        return "post/postAddForm";
    }
    @PostMapping("/post/add")
    public String addPost(@ModelAttribute("post") PostDTO postDTO) {
        boardService.addPost((postDTO));
        // 방금 쓴 글의 게시판 ID로 리다이렉트 (postDTO에 boardId가 담겨온다고 가정)
        return "redirect:/board/" + postDTO.getBoardId();
    }
    // 4. 게시판 상세 (특정 게시판 클릭 시)
    @GetMapping("/board/{boardId}")
    public String boardDetail(@PathVariable("boardId") int boardId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        int size = 10;
//        model.addAttribute("board", boardService.findOneBoard(boardId));
        model.addAttribute("postList", boardService.findPostsByBoardId(boardId, page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardService.getTotalPages(boardId, size));
        return "board/boardDetail";
    }

    // --- 여기서부터 순서가 중요합니다! ---



    // 6. 게시글 상세 및 댓글 (변수 경로 {postId}는 가장 아래에 배치)
    @GetMapping("/post/{postId}")
    public String postDetail(@PathVariable("postId") int postId, Model model) {
        boardService.increaseViewCount(postId);
        model.addAttribute("post", boardService.findOnePost(postId));
        model.addAttribute("commentList", boardService.findCommentsByPostId(postId));

        CommentDTO comment = new CommentDTO();
        comment.setPostId(postId);
        model.addAttribute("comment", comment);
        return "post/postDetail";
    }

    // ... 나머지 CUD 로직 (Post, Update, Delete) 유지
}