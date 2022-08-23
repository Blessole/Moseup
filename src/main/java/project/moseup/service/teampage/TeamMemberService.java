package project.moseup.service.teampage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamMember;
import project.moseup.dto.teamPage.TeamMemberDto;
import project.moseup.repository.teampage.TeamMemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamMemberService {

	private final TeamMemberRepository teamMemberRepository;
	
	// 팀 가입
	@Transactional
	public void joinTeamMember(TeamMemberDto teamMemberDto) {
		TeamMember teamMember = teamMemberDto.toEntity();
		teamMemberRepository.save(teamMember);
	}
}
