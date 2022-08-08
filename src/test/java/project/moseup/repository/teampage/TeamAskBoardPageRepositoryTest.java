package project.moseup.repository.teampage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;
import project.moseup.dto.teamPage.TeamAskBoardDto;
import project.moseup.repository.admin.AdminMemberRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamAskBoardPageRepositoryTest {

    @Autowired
    TeamAskBoardPageRepository teamAskBoardPageRepository;
    @Autowired
    AdminMemberRepository adminMemberRepository;


    @Test
    public void 삭제데이터준비_test(){
        Member member = adminMemberRepository.findById(35L).orElse(null);

        TeamAskBoardDto teamAskBoardDto = new TeamAskBoardDto();
        teamAskBoardDto.setTeamAskDate(LocalDate.now());
        teamAskBoardDto.setTeamAskContent("test");
        teamAskBoardDto.setTeamAskDelete(DeleteStatus.FALSE);
        teamAskBoardDto.setTeamAskSubject("test");
        teamAskBoardDto.setSecret(SecretStatus.PUBLIC);
        teamAskBoardDto.setTeamAskReadCount(0);
        teamAskBoardDto.setMember(member);

        TeamAskBoard teamAskBoard = teamAskBoardPageRepository.save(teamAskBoardDto.toEntity());

        assertEquals("test", teamAskBoard.getTeamAskContent());
    }


    @Test
    public void 빌더삭제_test(){
        TeamAskBoard teamAskBoard = teamAskBoardPageRepository.findById(40L).orElse(null);


       assertEquals(DeleteStatus.FALSE, teamAskBoard.getTeamAskDelete());


    }
}