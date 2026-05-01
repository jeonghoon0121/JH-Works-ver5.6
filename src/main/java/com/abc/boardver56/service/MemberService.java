package com.abc.boardver56.service;

import com.abc.boardver56.model.dao.MemberMapper;
import com.abc.boardver56.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Transactional
    public void register(MemberDto memberDto) {
        // 컨트롤러에서 미처 설정하지 못했을 경우를 대비한 방어 코드
        if (memberDto.getRole() == null) memberDto.setRole("USER");
        if (memberDto.getStatus() == null) memberDto.setStatus("ACTIVE");

        memberMapper.insertMember(memberDto);
    }

    /**
     * 로그인 검증 로직 추가
     * @param username 입력받은 아이디
     * @param password 입력받은 비밀번호
     * @return 로그인 성공 시 회원 정보 DTO, 실패 시 null
     */
    public MemberDto login(String username, String password) {
        // 1. DB에서 아이디를 기준으로 사용자 조회
        // (memberMapper에 findByUsername 메소드가 정의되어 있어야 합니다)
        MemberDto member = memberMapper.findByUsername(username);

        // 2. 사용자가 존재하고, 비밀번호가 일치하는지 비교
        if (member != null && member.getPassword().equals(password)) {
            // 비밀번호까지 일치하면 회원 객체 반환
            return member;
        }

        // 3. 아이디가 없거나 비밀번호가 틀리면 null 반환
        return null;
    }
}