package com.abc.boardver56.model.dao;

import com.abc.boardver56.model.dto.MemberDto;

public interface MemberMapper {
    // 회원가입
    void insertMember(MemberDto member);

    // 로그인 및 아이디 중복 확인을 위한 조회 메소드 추가
    MemberDto findByUsername(String username);
}
