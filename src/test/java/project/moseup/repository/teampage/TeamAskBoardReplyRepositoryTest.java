package project.moseup.repository.teampage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.*;
import project.moseup.repository.member.MemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
				.build();

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
		//memberRepository.save(member);
		teamAskBoardRepository.save(askboard);
		teamAskBoardReplyRepository.save(reply);
		
		
		// then
		assertEquals(askboard, reply.getTeamAskBoard());
	}



}
