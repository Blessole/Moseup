package project.moseup.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.repository.MemberRepository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void 멤버생성() throws Exception {
        // given
        Member member = Member.builder()
                .email("k1@k.com")
                .name("김솔")
                .password("1234")
                .nickname("밍구")
                .gender(MemberGender.FEMALE)
                .address("강남구")
                .phone("010-1234-1234")
                .memberDate(LocalDateTime.now())
                .memberDelete(DeleteStatus.FALSE)
                .build();

        // when
        //Long saveId = memberService.join(member);

        // then
        //assertEquals(member, memberRepository.findOne(saveId));
    }

}

