package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.dto.AskBoardReplySaveReqDto;
import project.moseup.repository.admin.AdminAskBoardReplyRepository;
import project.moseup.repository.myPage.AskBoardInterfaceRepository;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AskBoardReplyService {

    private final AdminAskBoardReplyRepository adminAskBoardReplyRepository;
    private final AskBoardInterfaceRepository askBoardInterfaceRepository;
    private final EntityManager entityManager;

    @Transactional(rollbackFor = RuntimeException.class)
    public void replyAdd(AskBoardReplySaveReqDto replySaveDto) {
        AskBoard askBoard = askBoardInterfaceRepository.findById(replySaveDto.getAskBoard().getAno()).orElse(null);
        AskBoardReply reply = adminAskBoardReplyRepository.findById(replySaveDto.getAskBoard().getAno()).orElse(null);

        if(askBoard.getAno() == reply.getAskBoard().getAno()){
            entityManager.merge(replySaveDto.toEntity());
        }else{
            entityManager.persist(replySaveDto.toEntity());
        }

    }
}
