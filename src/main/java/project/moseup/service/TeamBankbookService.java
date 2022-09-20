package project.moseup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.domain.TeamBankbook;
import project.moseup.dto.TeamBankBookReqDto;
import project.moseup.repository.TeamBankbookClassRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamBankbookService {
	
	private final TeamBankbookClassRepository teamBankbookClassRepository;
	
	/** 팀통장 입금 **/
	@Transactional
	public TeamBankbook create(Team team) {
		TeamBankBookReqDto teamBankBookReqDto = new TeamBankBookReqDto();
		teamBankBookReqDto.setTeam(team);
		
		TeamBankbook teamBankbook = teamBankBookReqDto.toEntity();
		teamBankbookClassRepository.save(teamBankbook);
		return teamBankbook;
	}
	
}
