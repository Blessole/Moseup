package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

@Repository
@RequiredArgsConstructor
public class TeamMemberRepository {
	
	private final EntityManager em;

	/*
	 * public void save(Member member, Team team) { em.persist(member, team); }
	 */

}
