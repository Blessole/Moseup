package project.moseup.repository.teampage;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.*;
import project.moseup.dto.TeamCreateReqDto;
import project.moseup.exception.MemberNotFoundException;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@SpringBootTest
@Transactional
public class TeamBankbookRepositoryTest {

    @Autowired
    TeamBankbookRepository teamBankbookRepository;
    @Autowired
    TeamInterfaceRepository teamInterfaceRepository;
    @Autowired
    AdminMemberRepository adminMemberRepository;
    @Autowired
    TeamBankbookDetailRepository teamBankbookDetailRepository;

    @Test
    public void 통장조회(){
        Team team1 = teamInterfaceRepository.findById(1L).orElse(null);
        Team team2 = teamInterfaceRepository.findById(12L).orElse(null);
        TeamBankbook teamBankbook = teamBankbookRepository.findByTeam(team2);

        log.info(teamBankbook.toString());

        assertThat(teamBankbook.getTeamBankbookDetails()).hasSize(1);

    }


    @Test
    public void 팀통장생성테스트(){
        Member member = adminMemberRepository.findById(4L).orElse(null);
        if(member == null){
            throw new MemberNotFoundException(4L);
        }

        TeamCreateReqDto saveDto = new TeamCreateReqDto();
        saveDto.setMember(member);
        saveDto.setTeamLeader(member.getNickname());
        saveDto.setTeamCategory3("코딩");
        saveDto.setTeamCategory2("개인");
        saveDto.setTeamCategory1("공부");
        saveDto.setEndDate(LocalDate.now());
        saveDto.setStartDate(LocalDate.now());
        saveDto.setTeamDate(LocalDate.now());
        saveDto.setTeamDelete(DeleteStatus.FALSE);
        saveDto.setTeamDeposit(2);
        saveDto.setTeamIntroduce("통장 테스트");
        saveDto.setTeamVolume(5);
        saveDto.setTeamName("통장 테스트");

        Team teamPS = teamInterfaceRepository.save(saveDto.teamBuilder());

        TeamBankbook teamBankbook = new TeamBankbook(teamPS);

        TeamBankbook teamBankbookPS = teamBankbookRepository.save(teamBankbook);

        assertThat(teamBankbookPS.getTeam().getTeamCategory1()).isEqualTo(teamPS.getTeamCategory1());
        assertThat(teamBankbookPS.getTeam().getTno()).isEqualTo(teamPS.getTno());

    }

    @Test
    public void 팀통장내역생성(){
        Member member = adminMemberRepository.findById(3L).orElse(null);
        TeamBankbook teamBankbook = teamBankbookRepository.findById(10L).orElse(null);

        TeamBankbookDetail detail = TeamBankbookDetail.builder()
                .member(member)
                .teamBankbook(teamBankbook)
                .teamBankbookDate(LocalDateTime.now())
                .dealList("머니 충전:-D")
                .teamBankbookDeposit(10000)
                .teamBankbookTotal(0)
                .teamBankbookWithdraw(0)
                .build();

        TeamBankbookDetail detailPS = teamBankbookDetailRepository.save(detail);

        assertThat(detailPS.getMember().getMno()).isEqualTo(member.getMno());
        assertThat(detailPS.getTeamBankbook().getTbno()).isEqualTo(teamBankbook.getTbno());
        assertThat(teamBankbook.getTeamBankbookDetails().get(0).getTeamBankbookDeposit()).isEqualTo(detailPS.getTeamBankbookDeposit());

    }

}