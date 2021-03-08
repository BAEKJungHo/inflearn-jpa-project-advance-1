package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 기존 트랜잭션 스크립트 패턴을 쓰는 경우에는 대부분 VO 객체만 선언해서 도메인+화면용으로 같이 쓰곤했는데
 * JPA 를 사용하면서 도메인 모델 패턴을 사용하며 + 타임리프를 사용하는 경우
 * 폼 전용 객체를 만들어서 여기에 javax.validation 애노테이션을 사용해서 화면단에서 사용할 수 있다.
 * VO 몰빵하는 경우에 javax.validation 애노테이션을 사용하여 검증을 실행하면 VO 가 애노테이션으로 도배될 가능성이 있어서 지저분해진다.
 * 하지만, 이 경우에는 도메인, 화면용, DTO 등으로 잘 나눠져있기 때문에 좀 더 효과적이다.
 */
@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
