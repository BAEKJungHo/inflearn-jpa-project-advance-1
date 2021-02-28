package jpabook.jpashop;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 에게 SpringBootTest 를 사용한다는 것을 알려준다.
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    /**
     * @Transactional 은 자바와 Spring 둘다 있는데 스프링을 사용하고 있으니 스프링으로 쓰는것을 권장
     * 쓸 수 있는 옵션이 많음
     * EntityManager 를 사용할 때 트랜잭션이 걸려있지 않으면
     * No EntityManager with actual transaction available ... 이라는 에러가 발생한다.
     * @Transactional 이 테스트 케이스에 있으면 테스트가 끝나고 Rollback 을 해버린다.
     * @Rollback(false) 을 하면 롤백을 하지 않는다.
     * @throws Exception
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember  = memberRepository.find(savedId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // 같은 영속성 컨텍스트 내에 있기 때문에 true
        System.out.println(findMember == member);
    }


}