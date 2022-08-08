package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.AskBoard;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardSaveReqDto;
import project.moseup.repository.myPage.AskBoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AskBoardService {

    private final AskBoardRepository askBoardRepository;

    /** 문의게시판 리스트 조회 **/
    public List<AskBoard> findAskBoards(Member member){
        return askBoardRepository.findAll(member);
    }

    /** 문의글 작성 **/
    @Transactional
    public void save(AskBoardSaveReqDto askBoardForm) {
        AskBoard askBoard = askBoardForm.toEntity();
        askBoardRepository.save(askBoard);
    }
}
