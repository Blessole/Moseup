package project.moseup.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.repository.MemberRepository;

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

    @Test
    @Rollback(false)
    public void 멤버생성() throws Exception {
        // given
//        Member member = new Member();
//        member.setEmail("k1@k.com");
//        member.setName("김솔");
//        member.setPassword("1234");
//        member.setNickname("밍구");
//        member.setGender(MemberGender.FEMALE);
//        member.setAddress("강남구");
//        member.setPhone("010-1234-1234");
//        member.setMemberDate(LocalDateTime.now());
//        member.setMemberDelete(DeleteStatus.FALSE);

        // when
//        Long saveId = memberService.join(member);
//
//        // then
//        assertEquals(member, memberRepository.findOne(saveId));
    }

}
