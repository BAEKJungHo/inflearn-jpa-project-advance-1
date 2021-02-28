package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * member 를 반환해도 되는데 id 만 반환하는 이유는
     * 커맨드와 쿼리를 분리해라 원칙에 의해
     * 저장을 하고나면 사이드 이펙트를 일으키는 커맨드 성이기 때문에 리턴값을 거의 안만든다.
     * 하지만 id 같은 경우는 다음에 조회할 수 있으니 id 만 리턴한다.
     *
     * persist 하는 순간에 @GeneratedValue 가 되어있으면 id 값을 만들어서 넣기 때문에 바로 꺼내 쓸 수 있다.
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
