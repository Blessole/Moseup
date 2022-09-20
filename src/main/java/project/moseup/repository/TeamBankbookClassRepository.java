package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamBankbook;

@Repository
@RequiredArgsConstructor
public class TeamBankbookClassRepository {
	
	private final EntityManager em;

	public void save(TeamBankbook teamBankbook) {
		em.persist(teamBankbook);
	}
	
}
