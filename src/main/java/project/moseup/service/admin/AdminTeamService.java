package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Team;
import project.moseup.domain.TeamBankbook;
import project.moseup.domain.TeamBankbookDetail;
import project.moseup.dto.teamPage.TeamDetailDto;
import project.moseup.repository.admin.AdminTeamRepository;
import project.moseup.repository.teampage.TeamBankbookDetailRepository;
import project.moseup.repository.teampage.TeamBankbookRepository;

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


    public TeamDetailDto teamDetail(Long id) {
        Optional<Team> teamOP = adminTeamRepository.findById(id);
        if (teamOP.isPresent()){
            Team teamPS = teamOP.get();
            return new TeamDetailDto().toDto(teamPS);
        }else{
            throw new NullPointerException("팀 정보가 없습니다 id = " + id);
        }
    }

    public Page<Team> teams(String keyword, Pageable pageable) {
        return adminTeamRepository.findByTeamNameContainingOrMemberNicknameContaining(keyword, keyword, pageable);
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
}
