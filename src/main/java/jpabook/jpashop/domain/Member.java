package jpabook.jpashop.domain;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    /**
     * mappedBy = "member" 내가 주인이 아니고 매핑된 거울일 뿐이라는 의미
     * 따라서 해당 변수에 값을 넣는다고 Order 의 Fk 가 변경되지 않는다.
     */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
