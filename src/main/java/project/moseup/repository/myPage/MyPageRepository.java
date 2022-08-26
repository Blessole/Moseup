package project.moseup.repository.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.moseup.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageRepository {
    private final EntityManager em;

    /** 가입한 팀 조회 **/
    public List<Team> findTeam(Member member){
        return em.createQuery("select t from Team t where t.tno in ( select tm.team.tno from TeamMember tm where tm.member = :member)", Team.class)
                .setParameter("member", member)
                .getResultList();
    }

}
