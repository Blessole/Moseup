package project.moseup.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.TeamBankbook;
import project.moseup.domain.TeamBankbookDetail;

@Repository
@RequiredArgsConstructor
public class TeamBankbookDetailClassRepository {

    private final EntityManager em;

	public void save(TeamBankbookDetail teamBankbookDetail) {
		em.persist(teamBankbookDetail);
	}
	
	public void withDraw(TeamBankbookDetail teamBankbookDetail) {
		em.persist(teamBankbookDetail);
	}

	public List<TeamBankbookDetail> findAll(TeamBankbook teamBankbook) {
		return em.createQuery("select tbd from TeamBankbookDetail tbd where teamBankbook =: teamBankbook",TeamBankbookDetail.class)
				.setParameter("teamBankbook", teamBankbook)
				.getResultList();
	}
}