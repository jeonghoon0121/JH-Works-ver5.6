package com.abc.boardver56.model.controller;

import com.abc.boardver56.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
@RequiredArgsConstructor // 서비스 주입을 위한 롬복 어노테이션
public class NavigationController {

    private final BoardService boardService;

    // 핵심: 이 컨트롤러 내의 모든 페이지 호출 시 boardList를 자동으로 모델에 담음
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("boardList", boardService.findAllBoards());
        // 서비스의 메서드명은 정훈님이 만드신 '전체 게시판 가져오기' 명칭으로 바꾸세요.
    }

    // --- [상위 메뉴 리다이렉트] ---
    @GetMapping("/about")
    public String aboutRoot() {
        return "redirect:/about/intro";
    }

    @GetMapping("/features")
    public String featuresRoot() {
        return "redirect:/board";
    }

    @GetMapping("/tools")
    public String toolsRoot() {
        return "redirect:/tools/calculator";
    }

    // --- [소개(About) 세부 항목] ---
    @GetMapping("/about/intro")
    public String intro() {
        return "about/intro";
    }

    @GetMapping("/about/techStack")
    public String techStack() {
        return "about/techStack";
    }

    @GetMapping("/about/requirements")
    public String requirements() {
        return "about/requirements";
    }

    @GetMapping("/about/workflow")
    public String workflow() {
        return "about/workflow";
    }

    @GetMapping("/about/architecture&DB")
    public String architecture() {
        return "about/architecture";
    }

    // --- [기능(Features) 세부 항목] ---
    @GetMapping("/features/board")
    public String boardFeature() {
        return "redirect:/board";
    }

    @GetMapping("/features/member")
    public String memberFeature() {
        return "redirect:/member/login";
    }

    @GetMapping("/features/admin")
    public String adminFeature() {
        return "redirect:/member/login";
    }

    // --- [도구(Tools) 세부 항목] ---
    @GetMapping("/tools/calculator")
    public String calculator() {
        return "tools/calculator";
    }

    @GetMapping("/dashboard")
    public String dashBoard() {
        return "layout/dashboard";
    }
}