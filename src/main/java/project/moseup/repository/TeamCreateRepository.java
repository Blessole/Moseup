package project.moseup.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;

@Repository
@RequiredArgsConstructor
public class TeamCreateRepository {

    private final EntityManager em;
    
    // 팀 저장
    public void save(Team team) {		
        em.persist(team);
    }

    // 멤버 아이디로 팀 검색
    public Team findOne(Long tno) {
        return em.find(Team.class, tno);
    }
    
    // teamName으로 팀 검색
    public List<Team> findByName(String teamName) {
		return em.createQuery("select t from Team t where t.teamName = :teamName", Team.class)
				.setParameter("teamName", teamName)
				.getResultList();
	}
    
}