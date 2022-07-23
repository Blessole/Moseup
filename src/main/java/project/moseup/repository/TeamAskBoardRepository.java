package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamAskBoard;

@Repository
@RequiredArgsConstructor
public class TeamAskBoardRepository {

	private final EntityManager em;
	
	public void save(TeamAskBoard teamAskBoard) {
		em.persist(teamAskBoard);
	}
}
