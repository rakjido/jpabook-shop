package io.rooftop.jpashop.service;

import io.rooftop.jpashop.domain.Address;
import io.rooftop.jpashop.domain.Member;
import io.rooftop.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception {
        // given
        Address address = new Address("Tokyo", "Chiyoda", "2423");
        Member member = new Member();
        member.setName("kim");
        member.setAddress(address);


        // when
        Long saveId = memberService.join(member);

        // then
//        assertEquals(member, memberRepository.findOne(saveId));
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 증복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2);
        // then
        fail("테스트 실패. 예외가 발생해야 한다.");
    }

//    @Test
//    public void 증복_회원_예외() throws Exception {
//        // given
//        Member member1 = new Member();
//        member1.setName("kim");
//
//        Member member2 = new Member();
//        member2.setName("kim");
//
//        // when
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }
//        // then
//        fail("테스트 실패. 예외가 발생해야 한다.");
//    }
}