package project.moseup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.TeamAskBoard;
import project.moseup.dto.teamPage.TeamAskBoardDto;
import project.moseup.repository.teampage.TeamAskBoardPageRepository;
import project.moseup.repository.teampage.TeamAskBoardRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamAskBoardService {

	private final TeamAskBoardRepository askBoardRepository;
	private final TeamAskBoardPageRepository askBoardPageRepository;





	// 글 등록
	@Transactional
	public void saveTeamAskBoard(TeamAskBoardDto teamAskBoardDto) {
		// Builder를 사용하여 다시 넣기
		TeamAskBoard teamAskBoard = teamAskBoardDto.toEntity();
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
