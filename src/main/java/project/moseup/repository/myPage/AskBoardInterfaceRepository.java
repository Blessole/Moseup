package project.moseup.repository.myPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.AskBoard;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;

import java.util.List;

public interface AskBoardInterfaceRepository extends JpaRepository<AskBoard, Long> {

    List<AskBoard> findByMemberAndAskDelete(Member member, DeleteStatus deleteStatus);

    /** 문의게시판 리스트 조회 + 페이징 **/
    Page<AskBoard>  findByMemberAndAskDeleteOrderByAnoDesc(Member member, DeleteStatus deleteStatus, Pageable pageable);

    Page<AskBoard> findByAskSubjectContainingOrAskContentContainingOrMemberNicknameContaining(String keyword, String keyword1, String keyword2, Pageable pageable);

    Page<AskBoard> findDistinctByAskBoardRepliesIsNotNull(Pageable pageable);

    Page<AskBoard> findDistinctByAskBoardRepliesIsNull(Pageable pageable);

    List<AskBoard> findDistinctByAskBoardRepliesIsNull();

    List<AskBoard> findDistinctByAskBoardRepliesIsNullOrderByAno();
}
