package project.moseup.service.teampage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Team;
import project.moseup.dto.teamPage.CheckBoardDto;
import project.moseup.repository.teampage.CheckBoardPageRepository;
import project.moseup.repository.teampage.CheckBoardRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckBoardService {

	private final CheckBoardRepository checkBoardRepository;
	private final CheckBoardPageRepository checkBoardPageRepository;
	
	// 인증 글 등록
	@Transactional
	public void saveCheckBoard(CheckBoardDto checkBoardDto) {
		CheckBoard checkBoard = checkBoardDto.toEntity();
		checkBoardRepository.save(checkBoard);
	}
	
	// 페이징 된 모든글 보여주기
	public Page<CheckBoard> findCheckBoardPage(Team team, Pageable pageable) {
		return checkBoardPageRepository.findByTeam(team, pageable);
	}
}
