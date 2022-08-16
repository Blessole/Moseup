package project.moseup.repository.teampage;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamAskBoardReply;

@Repository
@RequiredArgsConstructor
public class TeamAskBoardReplyRepository {

	private final EntityManager em;
	
	// 댓글 저장
	public void save(TeamAskBoardReply teamAskBoardReply) {
		if (teamAskBoardReply.getTarno() == null) {
			em.persist(teamAskBoardReply);
		} else {
			em.merge(teamAskBoardReply);
		}
	}
	
	// 댓글 목록
	public List<TeamAskBoardReply> findAll(){
		return em.createQuery("select a from TeamAskBoardReply a", TeamAskBoardReply.class).getResultList();
	}
	
	// 해당 게시글 댓글 목록
	/*
	 * public List<TeamAskBoardReply> findReplyAll(){ return
	 * em.createQuery("select a from TeamAskBoardReply a where",
	 * TeamAskBoardReply.class).getResultList(); }
	 */
	
	// 댓글 단건 조회
	public TeamAskBoardReply findOne(Long tarno) {
		return em.find(TeamAskBoardReply.class, tarno);
	}
}
