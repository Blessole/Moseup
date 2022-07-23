package project.moseup.service;

import java.util.List;

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
	public Long create(Team team) {		//팀 생성
		validateDuplicateTeam(team);  //팀명 중복 검증
		teamRepository.save(team);
		return team.getTno();
	}
	
	private void validateDuplicateTeam(Team team) {
		List<Team> findTeams = teamRepository.findByName(team.getTeamName());
		if(!findTeams.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 팀명입니다.");
		}
	}

	// 회원 단건 조회
	public Team findOne(Long tno) {
		return teamRepository.findOne(tno);
	}
}
