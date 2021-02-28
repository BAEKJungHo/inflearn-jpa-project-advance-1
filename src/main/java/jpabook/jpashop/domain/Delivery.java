package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    /**
     * enum 의 경우 @Enumerated 를 써줘야 하는데
     * 디폴트는 EnumType.ORDINAL 이다. 근데 얘는 1, 2, 3 이런식으로 들어가서
     * 만약에 중간에 새로운 타입이 생기면 기존 타입 번호가 뒤로 밀리기 때문에 문제가 발생한다.
     * 따라서 ORDINAL 은 절대 쓰면 안된다. 무조건 STRING 으로 써야 한다.
     */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus stauts; // READY(배송 준비), COMP(배송)

}
