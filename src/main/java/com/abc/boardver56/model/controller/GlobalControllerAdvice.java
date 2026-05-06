package com.abc.boardver56.model.controller;

import com.abc.boardver56.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 프로젝트 내 모든 컨트롤러에 공통 데이터를 주입합니다.
 * basePackages 설정을 통해 에러 처리(BasicErrorController) 시의 무한 루프를 방지합니다.
 */
@ControllerAdvice(basePackages = "com.abc.boardver56.model.controller")
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final BoardService boardService;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        // 모든 뷰(HTML)에서 'boardList'라는 이름으로 게시판 메뉴를 렌더링할 수 있습니다.
        model.addAttribute("boardList", boardService.findAllBoards());
    }
}