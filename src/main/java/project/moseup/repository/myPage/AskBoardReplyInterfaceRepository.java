package project.moseup.repository.myPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.DeleteStatus;
import project.moseup.service.myPage.AskBoardReplyService;

@Repository
public interface AskBoardReplyInterfaceRepository extends JpaRepository<AskBoardReply, Long> {

    /** 문의게시판 댓글 조회 + 페이징 **/
    Page<AskBoardReply> findByAskBoardAndAskReplyDelete(AskBoard askBoard, DeleteStatus aFalse, Pageable pageable);
}
