package project.moseup.repository.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.TeamForm;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional
public class AdminTeamRepositoryTest {

    @Autowired
    AdminTeamRepository adminTeamRepository;

    @Autowired
    AdminMemberRepository adminMemberRepository;


    @Test
    public void 길드등록_test(){
        Member member = adminMemberRepository.findById(35L).orElse(null);

        TeamForm teamForm = TeamForm.builder()
                .teamName("테스트")
                .member(member)
                .teamDate(LocalDate.now())
                .startDate(LocalDate.of(2022, 8, 11))
                .endDate(LocalDate.of(2022, 9, 11))
                .teamDeposit(10000)
                .teamVolume(10)
                .teamCategory1("카테고리1")
                .teamCategory2("카테고리2")
                .teamCategory3("카테고리3")
                .teamDelete(DeleteStatus.FALSE)
                .teamIntroduce("안뇽하세요 테스트 입니동")
                .build();
        Team team = adminTeamRepository.save(teamForm.teamBuilder());

        assertEquals("테스트", team.getTeamName());








    }
}