package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    // 스프링 부트에 의해 @PersistenceContext 없이 쓸 수 있다.
    private final EntityManager em;

    public void save(Member member) {
        // 영속성 객체에 Member 를 넣고 트랜잭션이 실행 끝나는 시점에 Insert 쿼리가 실행된다.
        // persist 에 넣을 때 Insert 쿼리가 실행되는게 아니다(물론 DB 전략마다 다름)
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // jpql : 조회 대상이 테이블이 아닌 entity 이다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
