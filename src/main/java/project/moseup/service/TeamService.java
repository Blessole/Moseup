package project.moseup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.repository.TeamRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

	private final TeamRepository teamRepository;

	@Transactional
	public Long create(Team team) {
		teamRepository.save(team);
		return team.getTno();
	}

	// 회원 단건 조회
	public Team findOne(Long tno) {
		return teamRepository.findOne(tno);
	}
}
