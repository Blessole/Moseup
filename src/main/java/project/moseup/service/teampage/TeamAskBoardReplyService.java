package project.moseup.service.teampage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Member;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamAskBoardReply;
import project.moseup.dto.TeamAskBoardReplySaveReqDto;
import project.moseup.dto.TeamAskReplyDeleteAndRecoverDto;
import project.moseup.dto.teamPage.TeamAskBoardReplyDto;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.repository.teampage.TeamAskBoardPageRepository;
import project.moseup.repository.teampage.TeamAskBoardReplyInterfaceRepository;
import project.moseup.repository.teampage.TeamAskBoardReplyRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamAskBoardReplyService {

	private final TeamAskBoardReplyRepository teamAskBoardReplyRepository;

	private final TeamAskBoardReplyInterfaceRepository teamAskBoardReplyInterfaceRepository;

	private final AdminMemberRepository adminMemberRepository;
	private final TeamAskBoardPageRepository teamAskBoardPageRepository;
	
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

	@Transactional(rollbackFor = RuntimeException.class)
    public void replyAdd(TeamAskBoardReplySaveReqDto saveReqDto) {
		Optional<Member> memberOP = adminMemberRepository.findById(saveReqDto.getMno());
		Optional<TeamAskBoard> teamAskBoardOP = teamAskBoardPageRepository.findById(saveReqDto.getTano());
		if(memberOP.isPresent() && teamAskBoardOP.isPresent()){

			saveReqDto.setMember(memberOP.get());
			saveReqDto.setTeamAskBoard(teamAskBoardOP.get());

			teamAskBoardReplyRepository.save(saveReqDto.toEntity());
		}else{
			throw new NullPointerException("회원 데이터 또는 팀 문의글 데이터가 존재하지 않습니다 \n" +
					"회원 ID = " + saveReqDto.getMno() + "팀문의글 ID = " + saveReqDto.getTano());
		}

    }

	@Transactional(rollbackFor = RuntimeException.class)
	public void deleteAndRecover(TeamAskReplyDeleteAndRecoverDto dto) {
		Optional<TeamAskBoardReply> teamAskBoardReplyOP = teamAskBoardReplyInterfaceRepository.findById(dto.getTarno());

		if(teamAskBoardReplyOP.isPresent()){
			TeamAskBoardReply teamAskBoardReplyPS = teamAskBoardReplyOP.get();

			switch (dto.getChoice()){
				case "delete" : teamAskBoardReplyPS.replyDelete();
					break;
				case "recover" : teamAskBoardReplyPS.replyRecover();
					break;
				default: break;
			}
		}else{
			throw new NullPointerException("해당 팀 문의댓글이 없습니다 ID = " + dto.getTarno());
		}

	}
}
