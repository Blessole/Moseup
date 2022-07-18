package project.moseup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import project.moseup.domain.Team;
import project.moseup.repository.MemberRepository;
import project.moseup.repository.TeamRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamServiceTest {
	
	@Autowired TeamService teamService;
	@Autowired TeamRepository teamRepository;
	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;
	
	@Test
	@Rollback(true)
	public void 팀생성() throws Exception {
		// given
		Member member = new Member();
		LocalDateTime date = LocalDateTime.now();
		member.setEmail("가글");
		member.setPassword("네이년");
		member.setNickname("널");
		member.setName("널");
		member.setGender(MemberGender.MALE);
		member.setAddress("서울");
		member.setPhone("010");
		member.setMdelete(DeleteStatus.FALSE);
		member.setDate(date);
		
		Team team = new Team();
		
		team.setTname("모습");
		team.setVolume(4);
		team.setTdeposit(0);
		team.setTeamDate(date);
		team.setStartDate(date);
		team.setEndDate(date);
		team.setIntroduce("반갑습니다.");
		team.setTdelete(DeleteStatus.FALSE);
		
		// when
		Long saveId = teamService.create(team);
		Long memberId = memberService.join(member);
		
		// then
		assertEquals(team, teamRepository.findOne(saveId));
		assertEquals(member, memberRepository.findOne(memberId));
		
	}

}
