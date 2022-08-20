package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.repository.myPage.CheckBoardInterfaceRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;
import project.moseup.repository.myPage.MyPageRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final TeamInterfaceRepository teamInterfaceRepository;
    private final CheckBoardInterfaceRepository checkBoardInterfaceRepository;

    /** 가입한 팀 조회 **/
    public List<Team> findTeam(Member member){
        return myPageRepository.findTeam(member);
    }

    /** 가입한 팀 조회 + 필터 + 페이징 **/
    public Page<Team> findTeamPaging(Member member, String sort, int startAt) {
        Pageable pageable = PageRequest.of(startAt, 5);
        Page<Team> teamList = null;
        LocalDate localDate = LocalDate.now();

        switch (sort) {
            case "none":
                System.out.println("MPS - switch : none 지나갑니다");
                teamList = teamInterfaceRepository.findTeamMember(member, pageable);
                break;
            case "myLead":
                System.out.println("MPS - switch : myLead 지나갑니다");
                teamList = teamInterfaceRepository.findByMember(member, pageable);
                break;
            case "future":
                System.out.println("MPS - switch : future 지나갑니다");
                teamList = teamInterfaceRepository.findByMemberAndStartDateAfter(member, localDate, pageable);
                break;
            case "current":
                System.out.println("MPS - switch : future 지나갑니다");
                teamList = teamInterfaceRepository.findByMemberAndStartDateBeforeAndEndDateAfter(member, localDate, pageable);
                break;
            case "past":
                System.out.println("MPS - switch : past 지나갑니다");
                teamList = teamInterfaceRepository.findByMemberAndEndDateBefore(member, localDate, pageable);
                break;
        }
        return teamList;
    }

//    /** 인증글 조회 **/
//    public List<CheckBoard> findCheckBoard(Member member) {
//        return myPageRepository.findCheckBoard(member);
//    }

    /** 인증글 조회 + 페이징 **/
    public Page<CheckBoard> findCheckBoardPaging(Member member, int startAt) {
        Pageable pageable = PageRequest.of(startAt, 6);
        return checkBoardInterfaceRepository.findByMember(member, pageable);
    }

    /** 팀별 인증글 조회 + 페이징 **/
    public Page<CheckBoard> findCheckBoardByTeamPaging(Member member, Long tno, int startAt){
        Pageable pageable = PageRequest.of(startAt, 6);
        Optional<Team> teamOptional = teamInterfaceRepository.findById(tno);
        Team team = teamOptional.get();
        System.out.println("MPS - team : "+ team.getTno());
        return checkBoardInterfaceRepository.findByMemberAndTeam(member, team, pageable);
    }



}
