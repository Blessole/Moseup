package project.moseup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Team;
import project.moseup.repository.TeamRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class TeamServiceTest {
	
	@Autowired TeamService teamService;
	@Autowired TeamRepository teamRepository;
	@Autowired EntityManager em;
	
	@Test
	@Rollback(false)
	public void 팀생성() throws Exception {
		// given
		Team team = new Team();
		team.setTname("모습");
		team.setVolume(4);
		team.setTdeposit(0);
		team.setTeamDate(null);
		team.setStartDate(null);
		team.setEndDate(null);
		team.setIntroduce("반갑습니다.");
		team.setDelete(DeleteStatus.FALSE);
		
		
		// when
		int saveId = teamService.create(team);
		
		// then
		assertEquals(team, teamRepository.findOne(saveId));
	}

}
