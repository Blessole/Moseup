package project.moseup.repository.myPage;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.AskBoard;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import java.util.List;

public interface AskBoardInterfaceRepository extends JpaRepository<AskBoard, Long> {

    List<AskBoard> findByMemberAndAskDelete(Member member, DeleteStatus deleteStatus);
}
