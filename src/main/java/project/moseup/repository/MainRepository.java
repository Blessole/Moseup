package project.moseup.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;

@Repository
@RequiredArgsConstructor
public class MainRepository {
	
	private final EntityManager em;
	
//	public List<Team> topList() {
//		String jpql = "select t from Team t join fetch t.teamMembers";
//		List<Team> results = em.createQuery(jpql, Team.class).getResultList();
//		return results;
//	}
	
	public List<Team> topList() {
		String jpql = "select t from Team t join t.teamMembers";
		//select * from Teams t join team_Members m where t.team_no= m.team_no;와 같은 값
		List<Team> results = em.createQuery(jpql, Team.class).getResultList();
		return results;
	}
	
}
