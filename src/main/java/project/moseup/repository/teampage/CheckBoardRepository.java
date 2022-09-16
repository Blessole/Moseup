package project.moseup.repository.teampage;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Team;

@Repository
@RequiredArgsConstructor
public class CheckBoardRepository {

	private final EntityManager em;
	
	// 인증글 저장
	public void save(CheckBoard checkBoard) {
		if(checkBoard.getCno() == null) {
			em.persist(checkBoard);
		} else {
			em.merge(checkBoard);
		}
	}
	
	// 인증글 1개 찾기
	public CheckBoard findOne(Long cno) {
		return em.find(CheckBoard.class, cno);
	}
	
	// 팀별 인증글 찾기
	public List<CheckBoard> findByTeam(Team team){
		return em.createQuery("select c from CheckBoard c where c.team =: team", CheckBoard.class)
				.setParameter("team", team).getResultList();				
	}
	
}
