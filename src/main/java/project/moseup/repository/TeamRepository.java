package project.moseup.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;

    public void save(Team team) {		// 팀 저장
        em.persist(team);
    }

    public Team findOne(Long tno) {			// 멤버 아이디로 팀 검색
        return em.find(Team.class, tno);
    }
    
    public List<Team> findByName(String teamName) {		// teamName으로 팀 검색
		return em.createQuery("select t from Team t where t.teamName = :teamName", Team.class)
				.setParameter("teamName", teamName)
				.getResultList();
	}
}
