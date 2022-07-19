package project.moseup.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.moseup.domain.Member;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final EntityManager entityManager;
	
	public Member save(Member member) {
		entityManager.persist(member);
		return member;
	}
	
	public Member findOne(Long mno) {
		return entityManager.find(Member.class, mno);
	}
}
