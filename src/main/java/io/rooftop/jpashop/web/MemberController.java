package io.rooftop.jpashop.web;

import io.rooftop.jpashop.domain.Address;
import io.rooftop.jpashop.domain.Member;
import io.rooftop.jpashop.service.MemberService;
import io.rooftop.jpashop.web.dto.MemberFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        model.addAttribute("memberForm", new MemberFormDto());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberFormDto form, BindingResult result) { //@Valid가 MemberForm안에 체크할 것을 검증한다. @Valid에서 에러가 있을 경우 Binding result
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        log.info(getClass().getName() + " / " + methodName);

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
