package project.moseup.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    /** 단건 조회(회원번호) **/
    public Member findOneMno(Long mno) {

        return em.find(Member.class, mno);
    }

    /** 단건 조회(아이디) - 로그인 **/
    public Member findOneEmail(String email){
        return em.createQuery("select m from Member m where m.email=:email", Member.class).setParameter("email", email).getSingleResult();
    }

    /** 전체 조회 **/
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    /** 중복 가입 방지 **/
    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email=:email", Member.class).setParameter("email", email).getResultList();
    }

}
