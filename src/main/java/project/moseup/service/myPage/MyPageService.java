package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.*;
import project.moseup.dto.*;
import project.moseup.repository.myPage.*;
import project.moseup.repository.teampage.TeamMemberRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final MyPageRepository myPageRepository;
    private final TeamInterfaceRepository teamInterfaceRepository;
    private final CheckBoardInterfaceRepository checkBoardInterfaceRepository;
    private final BankbookInterfaceRepository bankbookInterfaceRepository;
    private final TeamMemberInterfaceRepository teamMemberInterfaceRepository;

    private final TeamMemberRepository teamMemberRepository;
    private final LikeInterfaceRepository likeInterfaceRepository;

    /** 가입한 팀 조회 **/
    public List<Team> findTeam(Member member){
        return myPageRepository.findTeam(member);
    }

//    /** 가입한 팀 카운트 + 필터 **/
//    public int findTeamCount(Member member, String sort){
//        return myPageRepository
//    }

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
        return checkBoardInterfaceRepository.findByMemberAndTeam(member, team, pageable);
    }

    /** 내 통장 조회 + 페이징 **/
    public Page<Bankbook> findBankbookPaging(Member member, int startAt){
        Pageable pageable = PageRequest.of(startAt, 6);
        return bankbookInterfaceRepository.findByMemberOrderByDnoDesc(member, pageable);
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

    /** 회원 탈퇴 전 진행 중 팀 여부 찾기 **/
    public List<Team> beforeDelete(Member member) {
        LocalDate localDate = LocalDate.now();
        List<Team> teamList = teamInterfaceRepository.findByMemberAndStartDateBeforeAndEndDateAfter(member, localDate);

        return teamList;
    }

    /** 회원 탈퇴 전 진행 예정 팀멤버 delete 하기 **/
    @Transactional
    public int updateTeamMember(Member member){
        int result = 0;

//        TeamMemberReqDto dto = new TeamMemberReqDto();
        LocalDate localDate = LocalDate.now();
        List<TeamMember> teamMemberList = teamMemberInterfaceRepository.findByMemberAndStartDateAfter(member, localDate);
        if (teamMemberList.isEmpty()){
            result = 1;
        } else {
//            dto.setMember(member);
            for (TeamMember tm : teamMemberList) {
                tm.deleteUpdate(DeleteStatus.TRUE);
            }
//            teamMemberRepository.merge(dto.teamMemberDelete());
        }
        return result;
    }

    // 찜 추가/취소
    @Transactional
    public void likeUnlike(String name, Likes likes) {
        if(name.equals("unLike")){
            try {
                log.info("찜 취소 시작");
                likeInterfaceRepository.deleteAllByTeamAndMember(likes.getTeam(), likes.getMember());
                log.info("찜 취소 완료");
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            try{
                likeInterfaceRepository.save(likes);
                log.info("찜 추가 완료");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Optional<Team> getTeam(Long tno) {
        return teamInterfaceRepository.findById(tno);
    }
}
