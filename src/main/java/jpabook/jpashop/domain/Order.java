package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * CascadeType.ALL 은 컬렉션에 담겨있는 OrderItem 을
     * persist(orderItemA)
     * persist(orderItemB)
     * persist(orderItemC)
     * 이런식으로 할 필요없이
     * persist(order) 한방으로 끝낼 수 있다. delete 도 마찬가지
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 1:1 관계 에서는 FK 를 어디에 두든 상관은 없다.
     * 그래도 Access 를 많이 하는 곳에 두는 것이 좋다.
     *
     * OneToOne 에서 cascade = CascadeType.ALL 설정하면
     * order 만 persist 하면 delivery 도 persist 된다.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    // 양방향 관계 (1:1, 1:다, 다:1) 에서는 연과관계 메서드를 만드는 것이 좋다.

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    /**
     * 위 코드랑 동일
     */
    /*
    public static void main(String[] args) {
        Member member = new Member();
        Order order = new Order();
        member.getOrders().add(order);
        order.setMember(member);
    }
    */

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}