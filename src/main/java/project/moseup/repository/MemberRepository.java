package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;import project.moseup.domain.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
	
	private final EntityManager em;
	
	public void save(Member member) {		//회원가입
		em.persist(member);
	}

	public Member findOne(Long mno) {		//회원검색
		return em.find(Member.class, mno);
	}

}
