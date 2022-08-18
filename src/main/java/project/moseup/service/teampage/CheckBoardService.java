package project.moseup.service.teampage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.CheckBoard;
import project.moseup.dto.teamPage.CheckBoardDto;
import project.moseup.repository.teampage.CheckBoardRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckBoardService {

	private final CheckBoardRepository checkBoardRepository;
	
	// 인증 글 등록
	@Transactional
	public void saveCheckBoard(CheckBoardDto checkBoardDto) {
		CheckBoard checkBoard = checkBoardDto.toEntity();
		checkBoardRepository.save(checkBoard);
	}
}
