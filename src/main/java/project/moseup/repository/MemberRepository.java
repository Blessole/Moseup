package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    /** 단건 조회 **/
    public Member findOne(Long mno) {
        return em.find(Member.class, mno);
    }

    /** 전체 조회 **/
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    /** 중복 가입 방지 **/
    public List<Member> findByMno(Long mno) {
        return em.createQuery("select m from Member m where m.mno=:mno", Member.class).setParameter("mno", mno).getResultList();
    }
}
