package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Team;
import project.moseup.dto.teamPage.TeamDetailDto;
import project.moseup.repository.admin.AdminTeamRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminTeamService {

    private final AdminTeamRepository adminTeamRepository;


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
}
