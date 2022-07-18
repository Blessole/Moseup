package project.moseup.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	@Rollback(false)
	public void 회원가입() throws Exception {
		 //given
        Member member = new Member();
        LocalDateTime date = LocalDateTime.now();
                
        member.setEmail("구글");
        member.setPassword("1234");
        member.setNickname("testId");
        member.setName("kim");
        member.setGender(MemberGender.MALE);
        member.setAddress("의정부");
        member.setPhone("010-111-1111");
        member.setMemberDelete(DeleteStatus.FALSE);
        member.setMemberDate(date);
        
        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
	}

}
