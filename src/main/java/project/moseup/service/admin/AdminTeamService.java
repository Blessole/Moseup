package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Team;
import project.moseup.repository.admin.AdminTeamRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminTeamService {

    private final AdminTeamRepository adminTeamRepository;


    public Team teamDetail(Long tno) {
        Team team = adminTeamRepository.findById(tno).orElse(null);
        return team;
    }

    public Page<Team> teams(String keyword, Pageable pageable) {
        return adminTeamRepository.findByTeamNameContainingOrMemberNicknameContaining(keyword, keyword, pageable);
    }
}
