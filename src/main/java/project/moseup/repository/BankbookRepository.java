package project.moseup.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.moseup.domain.Bankbook;

import javax.persistence.EntityManager;

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
	

	// 입금하기
	public void deposit(Bankbook bankbook) {
		em.persist(bankbook);
	}
	

}
