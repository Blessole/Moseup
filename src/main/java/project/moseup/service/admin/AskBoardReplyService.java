package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.AskBoardReply;
import project.moseup.dto.AskBoardReplyRespDto;
import project.moseup.dto.AskBoardReplySaveReqDto;
import project.moseup.repository.admin.AdminAskBoardReplyRepository;
import project.moseup.repository.myPage.AskBoardInterfaceRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AskBoardReplyService {

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
