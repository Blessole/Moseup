package project.moseup.repository.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.repository.teampage.TeamAskBoardRepository;

@SpringBootTest
public class TeamAskBoardRepositoryTest {

    @Autowired
    TeamAskBoardRepository teamAskBoardRepository;
    @Autowired
    AdminMemberRepository adminMemberRepository;


    @Test
    public void 글작성_test(){
//        Member member = adminMemberRepository.findById(47L).orElse(null);
//        TeamAskBoard teamAskBoard = new TeamAskBoard(
//                member,
//                "test",
//                "test",
//                LocalDate.now(),
//                0,
//                SecretStatus.SECRET,
//                DeleteStatus.FALSE
//                );
//        teamAskBoardRepository.save(teamAskBoard);
//
//        assertEquals("test", teamAskBoard.getTeamAskSubject());
//        assertEquals("test", teamAskBoard.getTeamAskContent());
//
//        teamAskBoardRepository.findOne(teamAskBoard.getTano());
//
    }
}