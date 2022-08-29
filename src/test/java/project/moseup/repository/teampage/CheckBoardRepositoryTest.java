package project.moseup.repository.teampage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.repository.TeamCreateRepository;
import project.moseup.repository.member.MemberRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CheckBoardRepositoryTest {

	@Autowired
	CheckBoardRepository checkBoardRepository;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TeamCreateRepository teamRepository;
	
	@Test
	@Rollback(false)
	void 인증글등록테스트() {
		// given
		Long no = (long) 104;
		Member member =  memberRepository.findOneEmail("k4@k.com");
		Team team = teamRepository.findOne(no);
		
		CheckBoard chb = CheckBoard.createCheckBoard()
				.member(member)
				.team(team)
				.checkDate(LocalDateTime.now())
				.checkContent("실험용")
				.checkPhoto(".jpg")
				.checkReadCount(0)
				.checkLike(0).build();
		
		// when
		checkBoardRepository.save(chb);
		
		// then
		assertEquals(team, chb.getTeam());
	}

}
