package project.moseup.service.teampage;

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
	
	// 댓글 한건 조회
	public TeamAskBoardReply findReply(Long tarno) {
		return teamAskBoardReplyRepository.findOne(tarno);
	}
}
