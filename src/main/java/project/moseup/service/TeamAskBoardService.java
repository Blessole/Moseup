package project.moseup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamAskBoard;
import project.moseup.repository.TeamAskBoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamAskBoardService {

	private final TeamAskBoardRepository askBoardRepository;
	
	@Transactional
	public void saveTeamAskBoard(TeamAskBoard teamAskBoard) {
		askBoardRepository.save(teamAskBoard);
	}
}
