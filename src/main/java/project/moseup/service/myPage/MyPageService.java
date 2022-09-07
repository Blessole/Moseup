package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Bankbook;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.BankbookRespDto;
import project.moseup.dto.BankbookSaveReqDto;
import project.moseup.dto.CheckBoardRespDto;
import project.moseup.repository.myPage.BankbookInterfaceRepository;
import project.moseup.repository.myPage.CheckBoardInterfaceRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;
import project.moseup.repository.myPage.MyPageRepository;

import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final TeamInterfaceRepository teamInterfaceRepository;
    private final CheckBoardInterfaceRepository checkBoardInterfaceRepository;
    private final BankbookInterfaceRepository bankbookInterfaceRepository;

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
                teamList = teamInterfaceRepository.findTeamMember(member, pageable);
                break;
            case "myLead":
                teamList = teamInterfaceRepository.findByMember(member, pageable);
                break;
            case "future":
                teamList = teamInterfaceRepository.findByMemberAndStartDateAfter(member, localDate, pageable);
                break;
            case "current":
                teamList = teamInterfaceRepository.findByMemberAndStartDateBeforeAndEndDateAfter(member, localDate, pageable);
                break;
            case "past":
                teamList = teamInterfaceRepository.findByMemberAndEndDateBefore(member, localDate, pageable);
                break;
        }
        return teamList;
    }

    /** 인증글 조회 **/
    public List<CheckBoardRespDto> findCheckBoardList(Member member) {
        List<CheckBoard> checkBoardList = checkBoardInterfaceRepository.findByMember(member);
        List<CheckBoardRespDto> dtoList = new ArrayList<CheckBoardRespDto>();

        for (CheckBoard cb : checkBoardList){
            CheckBoardRespDto dto = new CheckBoardRespDto();
            dto = dto.toDto(cb);
            dtoList.add(dto);
        }
        return dtoList;
    }

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

    /** 내 통장 조회 + 페이징 **/
    public Page<Bankbook> findBankbookPaging(Member member, int startAt){
        Pageable pageable = PageRequest.of(startAt, 6);
        return bankbookInterfaceRepository.findByMember(member, pageable);
    }

    /** 내 통장에서 가장 마지막 거래기록 가져오기 (충전페이지 총액 용) **/
    public List<Bankbook> findBankbook(Member member) {
        return bankbookInterfaceRepository.findTop1ByMemberOrderByDnoDesc(member);
    }

    /** 머니 충전하기 **/
    @Transactional
    public void charge(BankbookSaveReqDto bankbookDto){
        Bankbook bankbook = bankbookDto.toEntity();
        bankbookInterfaceRepository.save(bankbook);
    }

    public Page<Team> getMyLikeList(Member member, int startAt) {
        Pageable pageable = PageRequest.of(startAt, 10);
        return teamInterfaceRepository.findMyLikeTeam(member, pageable);
    }
}
