package project.moseup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.repository.TeamRepository;
import project.moseup.repository.TeamSearchRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

	private final TeamRepository teamRepository;
	private final TeamSearchRepository teamSearchRepository;

	@Transactional
	public Long create(Team team) {		//팀 생성
		teamRepository.save(team);
		return team.getTno();
	}

	//회원 단건 조회
	public Team findOne(Long tno) {
		return teamRepository.findOne(tno);
	}
	
	//팀명 중복 체크
	public List<Team> validateDuplicateTeam(String teamName) {
		List<Team> findTeams = teamRepository.findByName(teamName);
		if(!findTeams.isEmpty()) {
			List<Team> team = findTeams;
			return team;
		}
		return null;
	}
	
	//keyword가 포함된 팀명 찾기
	public List<Team> teamSearch(String keyword) {
		List<Team> teams = teamSearchRepository.findByteamNameContaining(keyword);
		List<Team> emptyList = new ArrayList<>();

		if (!teams.isEmpty()) {
			List<Team> findTeamList = teams;
			return findTeamList;
		}
		return emptyList;
	}
}