package project.moseup.service.teampage;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamAskBoardReply;
import project.moseup.dto.teamPage.TeamAskBoardReplyDto;
import project.moseup.repository.teampage.TeamAskBoardReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamAskBoardReplyService {

	private final TeamAskBoardReplyRepository teamAskBoardReplyRepository;
	
	// 댓글 등록
	@Transactional
	public void saveTeamAskBoardReply(TeamAskBoardReplyDto teamAskBoardReplyDto) {
		TeamAskBoardReply teamAskBoardReply = teamAskBoardReplyDto.toEntity();
		teamAskBoardReplyRepository.save(teamAskBoardReply);
	}
	
	// 모든 댓글 보이기
	public List<TeamAskBoardReply> findReplys() {
		return teamAskBoardReplyRepository.findAll();
	}
	
	// 해당 게시글 댓글 보이기
	/*
	 * public List<TeamAskBoardReply> findAskReplys(Long tarno) { return
	 * teamAskBoardReplyRepository. }
	 */
	
	// 댓글 한건 조회
	public TeamAskBoardReply findReply(Long tarno) {
		return teamAskBoardReplyRepository.findOne(tarno);
	}
}
