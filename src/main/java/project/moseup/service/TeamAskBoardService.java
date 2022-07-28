package project.moseup.service;

import java.util.List;

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
	
	public List<TeamAskBoard> findTeamAsks() {
		return askBoardRepository.findAll();
	}
	
	public TeamAskBoard findOne(Long tano) {
		return askBoardRepository.findOne(tano);
	}
}
