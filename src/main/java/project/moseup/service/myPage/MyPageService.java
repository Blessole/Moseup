package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.repository.myPage.MyPageRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepository myPageRepository;

    /** 가입한 팀 조회 **/
    public List<Team> findByMember(Member member){
        return myPageRepository.findByMember(member);
    }

}
