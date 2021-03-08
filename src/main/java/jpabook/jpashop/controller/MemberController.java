package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members")
    public String list(Model model) {
        // 사실 이런경우도 그냥 엔티티로 뿌리기 보다는 화면에 뿌리기 위한 용도의 DTO 를 만들어서 뿌리는게 낫다.
        // 물론 엔티티에 손댈게 없고 그냥 뿌리기만 하면 되는 상황에서는 써도 상관은 없다.
        // 하지만 나중에 시스템이 커지게되면 이런 하나하나 작은 습관이 큰 영향을 미친다.
        // 템플릿 엔진에서 랜더링 하는 경우에는 어차피 서버에서 기능이 돌아가기 때문에 member 엔티티를 화면 템플릿에 전달해도 괜찮다.
        // 왜냐하면 어차피 서버에서 원하는 데이터만 찍어서 출력하기 때문이다.
        // 하지만 API 의 경우에는 이유를 불문하고 무조건 DTO 를 만들어서 처리해야한다. (API 의 경우 절대 엔티티를 외부로 반환하면 안된다.)
        // API 라는 것은 스펙(spec)인데, 만약에 엔티티에 password 라는 필드가 추가된 경우, 패스워드가 그대로 노출 될 수있고, API spec 이 변해버린다.
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    /**
     * thymeleaf 의존성을 받게되면 thymeleaf-spring 이라는 녀석도 들어있다.
     * 따라서 스프링이 검증에 실패한 BindingResult 나 Errors 를 화면단까지 끌고간다.
     * 따라서 어떤 에러가 발생했는지 화면에 뿌릴 수 있다.
     * @param form
     * @param result
     * @return
     */
    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName()); member.setAddress(address);
        memberService.join(member);

        return "redirect:/";
    }

}
