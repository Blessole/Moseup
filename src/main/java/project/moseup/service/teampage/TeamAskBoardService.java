package project.moseup.service.teampage;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamAskBoard;
import project.moseup.dto.teamPage.TeamAskBoardDeleteDto;
import project.moseup.dto.teamPage.TeamAskBoardDto;
import project.moseup.dto.teamPage.TeamAskBoardUpdateDto;
import project.moseup.repository.teampage.TeamAskBoardPageRepository;
import project.moseup.repository.teampage.TeamAskBoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamAskBoardService {

	private final TeamAskBoardRepository askBoardRepository;
	private final TeamAskBoardPageRepository askBoardPageRepository;
	
	// 글 등록
	// 데이터가 없으면 글 등록 데이터가 있으면 update하는 식으로 하나에 적을 수 있지 않을까?
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
	
	// 특정글 삭제 상태
	@Transactional
	public void changeDelete(TeamAskBoardDeleteDto teamAskBoardDeleteDto, Long tano) {
		TeamAskBoard teamAskBoard = askBoardPageRepository.findById(tano).orElse(null);		
		teamAskBoard.changeBoardDelete(teamAskBoardDeleteDto.getTeamAskDelete());
	}
	
	// 조회수 증가
	@Transactional
	public int increaseReadCount(Long tano) {
		return askBoardPageRepository.updateReadCount(tano);
	}
	
	// 특정 글 수정
	@Transactional
	public void changeUpdate(TeamAskBoardUpdateDto teamAskBoardUpdateDto, Long tano) {
		TeamAskBoard teamAskBoard = askBoardPageRepository.findById(tano).orElse(null);
		
		if(teamAskBoard != null) {
			teamAskBoard.changeBoardContent(teamAskBoardUpdateDto.getTeamAskSubject(), teamAskBoardUpdateDto.getTeamAskContent(), teamAskBoardUpdateDto.getSecret());
		}
	}
	
}
