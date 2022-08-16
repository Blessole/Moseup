package project.moseup.repository.teampage;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Role;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamAskBoardReply;
import project.moseup.repository.member.MemberRepository;

@SpringBootTest
@Transactional
class TeamAskBoardReplyRepositoryTest {

	@Autowired
	TeamAskBoardReplyRepository teamAskBoardReplyRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TeamAskBoardRepository teamAskBoardRepository;
	
	@Test
	@Rollback(false)
	void test() {
		// given
		Member member = Member.builder()
				.email("kt@k.com")
				.password("tttt")
				.nickname("test")
				.name("김테스트")
				.gender(MemberGender.MALE)
				.address("사당")
				.photo("jpg")
				.phone("010-1111-1111")
				.memberDate(LocalDateTime.now())
				.role(Role.USER)
				.memberDelete(DeleteStatus.FALSE).build();
		
		TeamAskBoard askboard = TeamAskBoard.creatTeamAskBoard()
				.member(member)
				.teamAskSubject("테스트용")
				.teamAskContent("test를 해보자!")
				.teamAskDate(LocalDate.now())
				.teamAskDelete(DeleteStatus.FALSE)
				.teamAskReadCount(0)
				.secret(SecretStatus.PUBLIC).build();
		
		TeamAskBoardReply reply = TeamAskBoardReply.createTeamAskBoardReply()
				.member(member)
				.teamAskBoard(askboard)
				.teamAskReplyContent("테스트용 작성")
				.teamAskReplyDate(LocalDateTime.now())
				.teamAskReplyDelete(DeleteStatus.FALSE).build();
		
		// when
		memberRepository.save(member);
		teamAskBoardRepository.save(askboard);
		teamAskBoardReplyRepository.save(reply);
		
		
		// then
		assertEquals(askboard, reply.getTeamAskBoard());
	}

}
