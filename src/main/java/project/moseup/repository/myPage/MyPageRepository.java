package project.moseup.repository.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamMember;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyPageRepository {
    private final EntityManager em;

    public List<Team> findByMember(Member member){
        return em.createQuery("select t from Team t where t.tno in ( select tm.team.tno from TeamMember tm where tm.member = :member)", Team.class)
                .setParameter("member", member)
                .getResultList();
    }
}
