package com.abc.boardver56.model.controller;

import com.abc.boardver56.model.dto.MemberDto;
import com.abc.boardver56.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 폼 보여주기
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        MemberDto dto = new MemberDto();
        dto.setRole("USER");     // 초기값 세팅 (hidden 필드용)
        dto.setStatus("ACTIVE"); // 초기값 세팅 (hidden 필드용)
        model.addAttribute("member", dto);
        return "member/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute MemberDto memberDto) {
        memberService.register(memberDto);
        return "redirect:/member/login"; // 가입 후 로그인 페이지로 리다이렉트
    }

    // 로그인 폼 보여주기
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "member/login";
    }

    // 로그인 처리 (핵심 로직)
    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session, Model model) {
        // 서비스에서 검증 (아이디와 비밀번호 전달)
        MemberDto loginMember = memberService.login(memberDto.getUsername(), memberDto.getPassword());

        if (loginMember != null) {
            // 로그인 성공: 세션에 회원 정보 저장
            // 세션에 저장된 이름 "loginMember"는 나중에 HTML에서 사용됩니다.
            session.setAttribute("loginMember", loginMember);
            return "redirect:/"; // 로그인 성공 후 메인 페이지로 이동
        } else {
            // 로그인 실패: 다시 로그인 페이지로 보내면서 에러 메시지 전달
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "member/login";
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션 무효화 (모든 데이터 삭제)
        session.invalidate();
        return "redirect:/"; // 로그아웃 후 메인 페이지로 이동
    }
}