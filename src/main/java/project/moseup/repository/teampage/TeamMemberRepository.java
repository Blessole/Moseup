package project.moseup.repository.teampage;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamMember;

@Repository
@RequiredArgsConstructor
public class TeamMemberRepository {

	private final EntityManager em;
	
	// 팀 멤버 저장
	public void save(TeamMember teamMember) {
		 em.persist(teamMember);
	}
}
