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
import project.moseup.repository.TeamRepository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamServiceTest {

    @Autowired
    TeamService teamService;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void 팀생성() throws Exception {
        // given
        Team team = new Team();
        team.setTeamName("모습");
        team.setTeamVolume(4);
        team.setTeamDeposit(0);
        team.setTeamDate(LocalDateTime.now());
        team.setStartDate(LocalDateTime.now());
        team.setEndDate(LocalDateTime.now());
        team.setTeamIntroduce("반갑습니다.");
        team.setTeamDelete(DeleteStatus.FALSE);


        // when
        Long saveId = teamService.create(team);

        // then
        assertEquals(team, teamRepository.findOne(saveId));
    }

}
