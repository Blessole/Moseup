package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.*;
import project.moseup.dto.searchDto.TeamSearchDto;
import project.moseup.dto.teamPage.TeamDetailRespDto;
import project.moseup.repository.admin.AdminTeamRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;
import project.moseup.repository.teampage.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminTeamService {

    private final AdminTeamRepository adminTeamRepository;
    private final TeamBankbookDetailRepository teamBankbookDetailRepository;
    private final TeamBankbookRepository teamBankbookRepository;
    private final CheckBoardPageRepository checkBoardPageRepository;
    private final TeamInterfaceRepository teamInterfaceRepository;
    private final TeamAskBoardPageRepository teamAskBoardPageRepository;

    private final TeamAskBoardReplyInterfaceRepository teamAskBoardReplyInterfaceRepository;

    public TeamDetailRespDto teamDetail(Long id) {
        Optional<Team> teamOP = adminTeamRepository.findById(id);
        if (teamOP.isPresent()){
            Team teamPS = teamOP.get();
            return new TeamDetailRespDto().toDto(teamPS);
        }else{
            throw new NullPointerException("팀 정보가 없습니다 id = " + id);
        }
    }

    public Page<Team> teams(TeamSearchDto searchDto, Pageable pageable) {
        Page<Team> teams;

        switch (searchDto.getOrderBy()){
            case "Recruiting": teams = adminTeamRepository.findByStartDateIsAfter(LocalDate.now(), pageable);
                break;
            case "Proceeding": teams = adminTeamRepository.findByEndDateIsAfter(LocalDate.now(), pageable);
                break;
            case "End": teams = adminTeamRepository.findByEndDateIsBefore(LocalDate.now(), pageable);
                break;
            default: teams = adminTeamRepository.
                    findByTeamNameContainingOrMemberNicknameContaining
                            (searchDto.getKeyword(), searchDto.getKeyword(), pageable);
                break;
        }
        if(searchDto.getStartDate() != null && searchDto.getEndDate() != null){
            LocalDate startDate = null;
            LocalDate endDate = null;
            try {
                startDate = LocalDate.parse(searchDto.getStartDate());
                endDate = LocalDate.parse(searchDto.getEndDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
            teams = adminTeamRepository.findByTeamDateBetween(startDate, endDate, pageable);
        }
        return teams;
    }

    public List<TeamBankbookDetail> teamBankbookDetails(Long tno) {
        Optional<Team> teamOP = adminTeamRepository.findById(tno);
        if(teamOP.isPresent()){
          Team teamPS = teamOP.get();
          return teamBankbookDetailRepository.findByTeamBankbookTeamOrderByTdnoDesc(teamPS);
        }else{
            throw new NullPointerException();
        }
    }

    // 팀 통장 정보 가져오기
    public Map<String, Object> getTeamBankbook(Long tno) {
        Map<String, Object> resultMap = new HashMap<>();
        TeamBankbook teamBankbook = null;
        int total = 0;
        Optional<Team> teamPS = adminTeamRepository.findById(tno);

        if(teamPS.isPresent()){
            teamBankbook = teamBankbookRepository.findByTeamOrderByTeamBankbookDetailsTdnoDesc(teamPS.get());
            resultMap.put("teamBankbook", teamBankbook);
        }

        if(teamBankbook != null && !teamBankbook.getTeamBankbookDetails().isEmpty()){
            int size = teamBankbook.getTeamBankbookDetails().size();
            total = teamBankbook.getTeamBankbookDetails().get(size - 1).getTeamBankbookTotal();
        }
        resultMap.put("total", total);

        return resultMap;
    }

    // 인증글 역순 가져오기
    public Map<String, Object> getCheckBoard(Long tno) {
        Map<String, Object> resultMap = new HashMap<>();
        Optional<Team> teamOP = teamInterfaceRepository.findById(tno);
        if(teamOP.isPresent()){
            List<CheckBoard> checkBoards = checkBoardPageRepository.findByTeamOrderByCnoDesc(teamOP.get());
            resultMap.put("checkBoards", checkBoards);
            return resultMap;

        }else{
            throw new NullPointerException("해당 팀이 없습니다 id = " + tno);
        }
    }

    // 문의글 역순 가져오기
    public Map<String, Object> getAskBoard(Long tno) {
        Map<String, Object> resultMap = new HashMap<>();
        Optional<Team> teamOP = teamInterfaceRepository.findById(tno);
        if(teamOP.isPresent()){
            List<TeamAskBoard> teamAskBoards = teamAskBoardPageRepository.findByTeamOrderByTanoDesc(teamOP.get());
            resultMap.put("teamAskBoards", teamAskBoards);
            return resultMap;

        }else{
            throw new NullPointerException("해당 팀이 없습니다 id = " + tno);
        }
    }

    // 문의글 정보 & 댓글 역순 가져오기
    public Map<String, Object> getAskBoardReply(Long tano) {
        Map<String, Object> resultMap = new HashMap<>();
        Optional<TeamAskBoard> teamAskBoardOP = teamAskBoardPageRepository.findById(tano);
        if(teamAskBoardOP.isPresent()){
            List<TeamAskBoardReply> teamAskBoardReplies =
                    teamAskBoardReplyInterfaceRepository.findByTeamAskBoardOrderByTarnoDesc(teamAskBoardOP.get());

            resultMap.put("teamAskBoard", teamAskBoardOP.get());
            resultMap.put("replies",teamAskBoardReplies);
            resultMap.put("deleteTrue", DeleteStatus.TRUE);

            return resultMap;
        }else{
            throw new NullPointerException("팀 문의글 데이터가 없습니다 id = " + tano);
        }
    }
}
