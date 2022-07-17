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

	private TeamRepository teamRepository;
	
	@Transactional
	public int create(Team team) {
		teamRepository.save(team);
		return team.getTno();
	}
}
