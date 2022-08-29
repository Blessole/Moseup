package project.moseup.service;


import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.member.MemberRepository;
import project.moseup.service.member.MemberService;
import static org.junit.Assert.assertEquals;
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
//
//         when
//        Long saveId = memberService.join(member);
//
//         then
//        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    @Rollback(false)
    public void 회원정보수정() throws Exception {
        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto();
        memberSaveReqDto.setName("사과1");
        memberSaveReqDto.setNickname("애플1");
        memberSaveReqDto.setGender(MemberGender.MALE);
        memberSaveReqDto.setPhone("01012341234");
        memberSaveReqDto.setAddress("강남구");
        memberSaveReqDto.setAddress2("영동대로");
        memberSaveReqDto.setPhoto("1.jpg");
        memberSaveReqDto.setPassword("12345678");
        memberSaveReqDto.setPassword2("12345678");

        memberService.update(memberSaveReqDto, 1L);

        Member member = memberRepository.findOneMno(1L);
        assertEquals(member.getName(),"사과1");
    }
}

