package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.DeleteStatus;
import project.moseup.repository.myPage.AskBoardReplyInterfaceRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AskBoardReplyService {

    private final AskBoardReplyInterfaceRepository askBoardReplyInterfaceRepository;

    /** 문의게시판 댓글 리스트 조회 + 페이징 **/
    public Page<AskBoardReply> findAll(AskBoard askBoard, int startAt) {
        Pageable pageable = PageRequest.of(startAt, 5);
        return askBoardReplyInterfaceRepository.findByAskBoardAndAskReplyDelete(askBoard, DeleteStatus.FALSE, pageable);
    }

    /** 문의게시판 댓글 작성 **/
    @Transactional
    public void saveAskBoardReply(AskBoardReply askBoardReply) {
        askBoardReplyInterfaceRepository.save(askBoardReply);
    }
}
