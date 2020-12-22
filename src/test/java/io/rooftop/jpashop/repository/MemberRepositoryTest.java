package io.rooftop.jpashop.repository;

import io.rooftop.jpashop.domain.Address;
import io.rooftop.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testMember() {
        // given
        Address address = new Address("TOKYO", "Chiyoda", "342532");
        Member member = new Member();
        member.setName("HERO");
        member.setAddress(address);

        // when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(saveId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);
        Assertions.assertThat(findMember.getAddress().getCity()).isEqualTo(address.getCity());
    }
}