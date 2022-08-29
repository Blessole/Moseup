package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamMember;

@Repository
@RequiredArgsConstructor
public class TeamMemberRepository {
	
	private final EntityManager em;

	public void save(TeamMember teamMember) {
		em.persist(teamMember);
	}
	 

}
