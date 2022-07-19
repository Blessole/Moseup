package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

	private final EntityManager em;
	
	public void save(Team team) {
		em.persist(team);
	}
	
	public Team findOne(Long tno) {
		return em.find(Team.class, tno);
	}
}
