package com.abc.boardver56.model.controller;

import com.abc.boardver56.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final BoardService boardService;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("boardList", boardService.findAllBoards());
    }
}