package project.moseup.repository.teampage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.*;
import project.moseup.dto.teamPage.TeamAskBoardDto;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamAskBoardPageRepositoryTest {

    @Autowired
    TeamAskBoardPageRepository teamAskBoardPageRepository;
    @Autowired
    AdminMemberRepository adminMemberRepository;

    @Autowired
    TeamInterfaceRepository teamInterfaceRepository;




    @Test
    public void 조회_test(){
        TeamAskBoard teamAskBoard = teamAskBoardPageRepository.findById(40L).orElse(null);

        assertEquals("test" ,teamAskBoard.getTeamAskSubject());
        assertEquals("32z" ,teamAskBoard.getMember().getNickname());

    }



    @Test
    public void 삭제데이터준비_test(){
        Member member = adminMemberRepository.findById(1L).orElse(null);
        Team team = teamInterfaceRepository.findById(12L).orElse(null);

        TeamAskBoardDto teamAskBoardDto = new TeamAskBoardDto();
        teamAskBoardDto.setTeam(team);
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

    @Test
    public void 조회테스트(){
        TeamAskBoard teamAskBoard = teamAskBoardPageRepository.findById(40L).orElse(null);

        System.out.println(teamAskBoard.toString());

        assertEquals("아니용", teamAskBoard.getTeamAskBoardReplies().get(0).getTeamAskReplyContent());

    }


}