package project.moseup.repository.teampage;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.CheckBoard;

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
	
}
