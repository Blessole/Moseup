package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamBankbookDetail;

@Repository
@RequiredArgsConstructor
public class TeamBankbookDetailClassRepository {

    private final EntityManager em;

	public void save(TeamBankbookDetail teamBankbookDetail) {
		em.persist(teamBankbookDetail);
	}
    
}