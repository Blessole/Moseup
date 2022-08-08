package project.moseup.repository.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.moseup.domain.AskBoard;
import project.moseup.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AskBoardRepository {

    private final EntityManager em;

    /** 문의글 전체 리스트 조회 **/
    public List<AskBoard> findAll(Member member) {
        return em.createQuery("select a from AskBoard a where a.member=:member", AskBoard.class).setParameter("member", member).getResultList();
    }

    /** 문의글 저장 **/
    public void save(AskBoard askBoard) {
        em.persist(askBoard);
    }
}
