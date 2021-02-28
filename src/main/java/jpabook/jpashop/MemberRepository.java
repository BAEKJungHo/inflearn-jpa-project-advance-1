package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    /**
     * 스프링 부트는 모두 스프링 컨테이너 위에서 돌아가기 때문에
     * @PersistenceContext 를 붙여주면 EntityManager 를 주입시켜준다.
     */
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * member 를 반환해도 되는데 id 만 반환하는 이유는
     * 커맨드와 쿼리를 분리해라 원칙에 의해
     * 저장을 하고나면 사이드 이펙트를 일으키는 커맨드 성이기 때문에 리턴값을 거의 안만든다.
     * 하지만 id 같은 경우는 다음에 조회할 수 있으니 id 만 리턴한다.
     * @param member
     * @return
     */
    public Long save(Member member) {
        entityManager.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return entityManager.find(Member.class, id);
    }

}
