package com.banana.Bathbomb.service;
import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    public int join(Member member){
        validateDuplicateMember(member);    //중복 회원 검증
        return memberRepository.save(member);
    }

    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findById(member.getMemberId());
        if(findMember != null){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    /**
     * 회원 정보 조회
     */
    public Member findMember(int uid){
        return memberRepository.findOne(uid);
    }

    /**
     * 회원 정보 조회 by id
     */
    public Member findMemberById(String memberId){
        return memberRepository.findById(memberId);
    }

    /**
     * 회원 정보 수정
     */
    public int updateMember(Member member){
        return memberRepository.update(member);
    }

    /**
     * 회원 탈퇴
     */
    public int deleteMember(int memberUid){
        return memberRepository.delete(memberUid);
    }

}
