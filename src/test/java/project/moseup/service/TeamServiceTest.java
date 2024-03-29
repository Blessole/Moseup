/*
package project.moseup.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Team;
import project.moseup.repository.member.MemberRepository;
import project.moseup.repository.TeamRepository;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


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
	@Rollback(false)
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
		member.setMemberDelete(DeleteStatus.FALSE);
		member.setMemberDate(date);

		Team team = new Team();

		team.setTeamName("모습");
		team.setTeamVolume(4);
		team.setTeamDeposit(0);
		team.setTeamDate(date);
		team.setStartDate(date);
		team.setEndDate(date);
		team.setTeamIntroduce("반갑습니다.");
		team.setTeamDelete(DeleteStatus.FALSE);

		// when
		Long saveId = teamService.create(team);
		Long memberId = memberService.join(member);
		em.flush();

		// then
		assertEquals(team, teamRepository.findOne(saveId));
		assertEquals(member, memberRepository.findOne(memberId));

	}

}
*/
