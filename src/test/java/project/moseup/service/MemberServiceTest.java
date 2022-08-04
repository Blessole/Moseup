package project.moseup.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import project.moseup.repository.member.MemberRepository;
import project.moseup.service.member.MemberService;

import javax.persistence.EntityManager;

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
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Rollback(false)
    public void 멤버생성() throws Exception {
        // given
//        JoinForm joinForm = new JoinForm();
//        joinForm.setEmail("k1@k.com");
//        joinForm.setName("김솔");
//        joinForm.setPassword("1234");
//        joinForm.setNickname("밍구");
//        joinForm.setGender(MemberGender.FEMALE);
//        joinForm.setAddress("강남구");
//        joinForm.setPhone("010-1234-1234");

        // when
        //Long saveId = memberService.join(member);

        // then
        //assertEquals(member, memberRepository.findOne(saveId));
    }
}

