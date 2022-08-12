package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.AskBoard;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardRespDto;
import project.moseup.dto.AskBoardSaveReqDto;
import project.moseup.repository.myPage.AskBoardInterfaceRepository;
import project.moseup.repository.myPage.AskBoardRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AskBoardService {

    private final AskBoardRepository askBoardRepository;
    private final AskBoardInterfaceRepository askBoardInterfaceRepository;

    /** 문의게시판 리스트 조회 **/
    public List<AskBoard> findAskBoards(Member member){
        return askBoardInterfaceRepository.findByMemberAndAskDelete(member, DeleteStatus.FALSE);
    }

    /** 문의글 하나 조회 **/
    public AskBoard findOne(Long ano) {
        return askBoardRepository.findOne(ano);
    }

    /** DTO - 수정용 문의글 하나 조회 **/
    public AskBoardSaveReqDto getPost(Long ano) {
        Optional<AskBoard> askBoardWrapper = askBoardInterfaceRepository.findById(ano);
        AskBoard askBoard = askBoardWrapper.get();

        AskBoardSaveReqDto askBoardDto = AskBoardSaveReqDto.askBoardSave()
                .member(askBoard.getMember())
                .askSubject(askBoard.getAskSubject())
                .askContent(askBoard.getAskContent())
                .askPhoto(askBoard.getAskPhoto())
                .askDate(askBoard.getAskDate())
                .askDelete(askBoard.getAskDelete())
                .build();
        return askBoardDto;
    }

    /** 문의글 작성 **/
    @Transactional
    public void save(AskBoardSaveReqDto askBoardForm) {
        askBoardRepository.save(askBoardForm.toEntity());
    }
    
    /** 문의글 수정 **/
    @Transactional
    public Long update(AskBoardSaveReqDto askBoardDto, Long ano) {
        AskBoard askBoard = askBoardInterfaceRepository.findById(ano).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        askBoard.update(askBoardDto.getAskSubject(), askBoardDto.getAskContent(), askBoardDto.getAskPhoto());

        return ano;
    }

    /** DTO - 문의글 삭제 **/
    @Transactional
    public void deleteBoard(Long ano) {
        AskBoard askBoard = askBoardInterfaceRepository.findById(ano).orElseThrow(()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
        askBoard.delete();
        askBoardRepository.save(askBoard);
    }
}
