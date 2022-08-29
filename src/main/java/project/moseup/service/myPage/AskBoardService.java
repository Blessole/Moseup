package project.moseup.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if(askBoardWrapper.isPresent()){
            AskBoard askBoard = askBoardWrapper.get();

            return AskBoardSaveReqDto.askBoardSave()
                    .member(askBoard.getMember())
                    .askSubject(askBoard.getAskSubject())
                    .askContent(askBoard.getAskContent())
                    .askPhoto(askBoard.getAskPhoto())
                    .askDate(askBoard.getAskDate())
                    .askDelete(askBoard.getAskDelete())
                    .build();
        }
        return null;
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

    // 관리자페이지 문의글 리스트 출력
    public Page<AskBoard> askBoards(String keyword, Pageable pageable) {
        return askBoardInterfaceRepository.findByAskSubjectContainingOrAskContentContainingOrMemberNicknameContaining(keyword, keyword, keyword, pageable);
    }

    // 관리자 페이지 문의글 디테일
    public AskBoardRespDto getAskBoard(Long ano) {
        AskBoard askBoard = askBoardInterfaceRepository.findById(ano).orElse(null);
        if(askBoard != null){
            return new AskBoardRespDto().toDto(askBoard);
        }else{
            throw new RuntimeException("문의글 데이터가 없습니다");
        }
    }





}
