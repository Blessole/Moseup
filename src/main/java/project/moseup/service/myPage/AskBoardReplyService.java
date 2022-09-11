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
import project.moseup.dto.AskBoardReplyRespDto;
import project.moseup.dto.AskBoardReplySaveReqDto;
import project.moseup.repository.admin.AdminAskBoardReplyRepository;
import project.moseup.repository.myPage.AskBoardInterfaceRepository;
import project.moseup.repository.myPage.AskBoardReplyInterfaceRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    /** 문의게시판 댓글 리스트 조회 **/
    public List<AskBoardReply> findAll(AskBoard askBoard) {
        return askBoardReplyInterfaceRepository.findAllByAskBoardAndAskReplyDelete(askBoard, DeleteStatus.FALSE);
    }

//    public boolean getAskBoardReply(AskBoard askBoard){
//        askBoardReplyInterfaceRepository.findAskBoardReplyByAskBoardAndAndAskReplyDelete(askBoard, DeleteStatus.FALSE);
//        if () {
//            return true;
//        } else {
//            return false;
//        }
//    }


    /** 문의게시판 댓글 작성 **/
    @Transactional
    public void saveAskBoardReply(AskBoardReply askBoardReply) {
        askBoardReplyInterfaceRepository.save(askBoardReply);
    }

    // 여기부터 찬우 코드 ---------------//
    private final AdminAskBoardReplyRepository adminAskBoardReplyRepository;
    private final AskBoardInterfaceRepository askBoardInterfaceRepository;
    private final EntityManager entityManager;

    @Transactional(rollbackFor = RuntimeException.class)
    public void replyAdd(AskBoardReplySaveReqDto replySaveDto) {
        adminAskBoardReplyRepository.save(replySaveDto.toEntity());

    }

    // 댓글 가져오기
    public AskBoardReplyRespDto getAskBoardReply(Long arno) {
        Optional<AskBoardReply> askBoardReplyOP = adminAskBoardReplyRepository.findById(arno);
        if(askBoardReplyOP.isPresent()){
            AskBoardReply askBoardReplyPS = askBoardReplyOP.get();
            return new AskBoardReplyRespDto().toDto(askBoardReplyPS);
        }else{
            throw new IllegalStateException("찾는 댓글이 없습니다");
        }

    }
}
