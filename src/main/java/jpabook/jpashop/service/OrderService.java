package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    // 주문 취소는 주문, 취소, 검색이 가장 중요하다.

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 서비스의 주문과 주문 취소 메서드를 보면 비즈니스 로직 대부분이 엔티티에 있다. 서비스 계층
     * 은 단순히 엔티티에 필요한 요청을 위임하는 역할을 한다. 이처럼 엔티티가 비즈니스 로직을 가지고 객체 지
     * 향의 특성을 적극 활용하는 것을 도메인 모델 패턴(http://martinfowler.com/eaaCatalog/
     * domainModel.html)이라 한다. 반대로 엔티티에는 비즈니스 로직이 거의 없고 서비스 계층에서 대부분
     * 의 비즈니스 로직을 처리하는 것을 트랜잭션 스크립트 패턴(http://martinfowler.com/eaaCatalog/
     * transactionScript.html)이라 한다.
     *
     * 실제로 실무에서 JPA 를 사용한다 하더라고 문맥에 따라 도메인 모델 패턴을 쓸 건지, 트랜잭션 스크립트 패턴을 쓸 건지 정해야 하며
     * 둘이 양립을 한다.
     */

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 도메인에 @NoArgsConstructor(access = AccessLevel.PROTECTED) 선언이 되어있으면 protected 제한자로 생성자가 생성된다.
        // JPA 에서는 생성자가 protected 가 되어있으면, 도메인을 생성하지말고 해당 도메인에 있는 생성 메서드를 사용하라는 의미이다.
        // 따라서 위 처럼 Order.createOrder(member, delivery, orderItem); 이런식으로 제약 스타일로 코딩을 해야 유지보수와 설계를 더 좋게 끌어 갈 수 있다.
        // Order order1 = new Order();
        // OrderItem orderItme1 = new OrderItem();

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();

        // 위 코드를 SQL 을 직접 다루는 MyBatis, JDBC Template 등으로 할 때에는 cancel 비지니스 로직을 호출하고나서
        // 직접 DB 에 update 해주는 쿼리 등을(비지니스로직) 추가로 service 단에서 해줘야 DB 에 반영이된다.
        // 따라서 SQL 을 직접 다루는 경우에는 비지니스로직이 service 단에 몰려있다.
        // 하지만 JPA 의 경우 entity 의 data 만 바꾸면 바꾼 데이터에 해당하는 update query 가 알아서 날라가서 적용된다.
    }

    /** 주문 검색 */
/*
 public List<Order> findOrders(OrderSearch orderSearch) {
 return orderRepository.findAll(orderSearch);
 }
*/


}
