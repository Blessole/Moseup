package project.moseup.service.teampage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;
import project.moseup.dto.TeamMemberReqDto;
import project.moseup.dto.teamPage.TeamMemberDto;
import project.moseup.repository.teampage.TeamMemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamMemberService {

	private final TeamMemberRepository teamMemberRepository;
	
	// 팀 가입 (가입된 인원 추가, 중복 가입 방지)
	@Transactional
	public void joinTeamMember(TeamMemberDto teamMemberDto) {
		TeamMember teamMember = teamMemberDto.toEntity();
		teamMemberRepository.save(teamMember);
	}

	//팀멤버 생성
	@Transactional
	public void create(Member member, Team team) {

		TeamMemberReqDto teamMemberReqDto = new TeamMemberReqDto();
		teamMemberReqDto.setMember(member);
		teamMemberReqDto.setTeam(team);

		TeamMember teamMember = teamMemberReqDto.teamMemberBuilder();

		teamMemberRepository.save(teamMember);
	}
}
