package project.moseup.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamAskBoard;
import project.moseup.repository.teampage.TeamAskBoardPageRepository;
import project.moseup.repository.teampage.TeamAskBoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamAskBoardService {

	private final TeamAskBoardRepository askBoardRepository;
	private final TeamAskBoardPageRepository askBoardPageRepository;
	
	// 글 등록
	@Transactional
	public void saveTeamAskBoard(TeamAskBoard teamAskBoard) {
		askBoardRepository.save(teamAskBoard);
	}
	
	// 모든 글 찾기
	public List<TeamAskBoard> findTeamAsks() {
		return askBoardRepository.findAll();
	}
	
	// 모든글 보여주기(페이징)
	public Page<TeamAskBoard> findTeamAsksPage(Pageable pageable) {
		return askBoardPageRepository.findAll(pageable);
	}
	
	// 특정 글 1개 찾기
	public TeamAskBoard findOne(Long tano) {
		return askBoardRepository.findOne(tano);
	}
	
}
