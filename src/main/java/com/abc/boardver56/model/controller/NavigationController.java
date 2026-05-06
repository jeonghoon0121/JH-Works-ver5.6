package com.abc.boardver56.model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    // --- [소개(About) 섹션] ---
    @GetMapping("/about")
    public String aboutRoot() { return "redirect:/about/intro"; }

    @GetMapping("/about/intro")
    public String intro() { return "about/intro"; }

    @GetMapping("/about/techStack")
    public String techStack() { return "about/techStack"; }

    @GetMapping("/about/requirements")
    public String requirements() { return "about/requirements"; }

    @GetMapping("/about/workflow")
    public String workflow() { return "about/workflow"; }

    @GetMapping("/about/architecture&DB")
    public String architecture() { return "about/architecture"; }

    // --- [기능 및 도구 섹션] ---
    @GetMapping("/features")
    public String featuresRoot() { return "redirect:/board"; }

    @GetMapping("/tools")
    public String toolsRoot() { return "redirect:/tools/calculator"; }

    @GetMapping("/tools/calculator")
    public String calculator() { return "tools/calculator"; }

    @GetMapping("/dashboard")
    public String dashBoard() { return "layout/dashboard"; }
}