package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Bankbook;

@Repository
@RequiredArgsConstructor
public class BankbookRepository {
	
	private final EntityManager em;

	/** 통장 단건 조회 **/
	public Bankbook findByMember(Long mno) {
		return em.find(Bankbook.class, mno);
	}

	/** 출금하기 **/
	public void withdrawMerge(Bankbook bankbook) {
		em.persist(bankbook);
	}
	
}
