package project.moseup.repository.member;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /** 회원가입, 수정, 회원탈퇴 **/
    public void save(Member member) {
        if (member.getMno() != null){
            em.merge(member);
        } else {
            em.persist(member);
        }
    }

    /** 단건 조회(회원번호) **/
    public Member findOneMno(Long mno) {
        return em.find(Member.class, mno);
    }

    /** 단건 조회(아이디) **/
    public Member findOneEmail(String email){
        return em.createQuery("select m from Member m where m.email=:email", Member.class).setParameter("email", email).getSingleResult();
    }

    /** 전체 조회 **/
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    /** 중복 이메일 검증 **/
    public List<Member> findByEmail(String value) {
        return em.createQuery("select m from Member m where m.email=:value", Member.class).setParameter("value", value).getResultList();
    }

    /** 중복 닉네임 검증 **/
    public List<Member> findByNickname(String value) {
        return em.createQuery("select m from Member m where m.nickname=:value", Member.class).setParameter("value", value).getResultList();
    }

}
