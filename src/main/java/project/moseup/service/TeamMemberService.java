package project.moseup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.repository.TeamMemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamMemberService {
	
	private final TeamMemberRepository  teamMemberRepository;

	/*
	 * public void create(Member member, Team team) {
	 * teamMemberRepository.save(member, team); }
	 */
}
