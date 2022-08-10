package project.moseup.repository.teampage;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamAskBoard;

@Repository
@RequiredArgsConstructor
public class TeamAskBoardRepository {

	private final EntityManager em;
	
	// 문의글 저장
	public void save(TeamAskBoard teamAskBoard) {
		if (teamAskBoard.getTano() == null) {
			em.persist(teamAskBoard);
		} else {
			em.merge(teamAskBoard);
		}
	}
	
	// 문의글 목록
	public List<TeamAskBoard> findAll(){
		return em.createQuery("select a from TeamAskBoard a", TeamAskBoard.class).getResultList();
	}
	
	// 문의글 1개 찾기
	public TeamAskBoard findOne(Long tano) {
		return em.find(TeamAskBoard.class, tano);
	}
	
	// 문의글 1개 찾기(dto)
	/*
	 * public TeamAskBoardDetailDto findAsk(Long tano) { return
	 * em.createQuery("select a from TeamAskBoard a where a.tano = :tano",
	 * TeamAskBoardDetailDto.class).setParameter("tano", tano).getSingleResult(); }
	 */
	
}
