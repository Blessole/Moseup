package project.moseup.repository.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardSaveReqDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdminAskBoardRepositoryTest {

    @Autowired
    AdminAskBoardRepository adminAskBoardRepository;
    @Autowired
    AdminMemberRepository adminMemberRepository;
    @Autowired
    AdminAskBoardReplyRepository adminAskBoardReplyRepository;


    @Test
    public void 문의글생성(){
        Member member = adminMemberRepository.findById(1L).orElse(null);
        AskBoardSaveReqDto saveReqDto = AskBoardSaveReqDto.askBoardSave()
                .member(member)
                .askContent("테스트")
                .askDate(LocalDateTime.now())
                .askPhoto(null)
                .askSubject("테스트")
                .askDelete(DeleteStatus.FALSE)
                .build();
        adminAskBoardRepository.save(saveReqDto.toEntity());
    }

    @Test
    public void 댓글작성(){
        Member member = adminMemberRepository.findById(2L).orElse(null);

        AskBoard askBoard = adminAskBoardRepository.findById(1L).orElse(null);

        AskBoardReply askBoardReply = AskBoardReply.builder()
                .askBoard(askBoard)
                .member(member)
                .askReplyContent("안녕하세요")
                .askReplyDate(LocalDateTime.now())
                .askReplyDelete(DeleteStatus.FALSE)
                .build();
        adminAskBoardReplyRepository.save(askBoardReply);

        askBoard.addReply((List<AskBoardReply>) askBoardReply);

        assertThat(askBoard.getAno()).isEqualTo(askBoardReply.getAskBoard().getAno());


    }


}