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
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardRespDto;
import project.moseup.dto.AskBoardSaveReqDto;
import project.moseup.dto.searchDto.AskBoardSearchDto;
import project.moseup.repository.myPage.AskBoardInterfaceRepository;
import project.moseup.repository.myPage.AskBoardReplyInterfaceRepository;
import project.moseup.repository.myPage.AskBoardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AskBoardService {

    private final AskBoardRepository askBoardRepository;
    private final AskBoardInterfaceRepository askBoardInterfaceRepository;
    private final AskBoardReplyInterfaceRepository askBoardReplyInterfaceRepository;

//    /** 문의게시판 리스트 조회 **/
//    public List<AskBoard> findAskBoards(Member member){
//        return askBoardInterfaceRepository.findByMemberAndAskDelete(member, DeleteStatus.FALSE);
//    }

    /** 문의게시판 리스트 조회 + 페이징 **/
    // startAt : 파라미터 통해 받은 현재 페이지
    // 10 : 10개씩 출력해라~
    public Page<AskBoard> findAskBoardsPaging(Member member, int startAt){
        Pageable pageable = PageRequest.of(startAt, 10);
        return askBoardInterfaceRepository.findByMemberAndAskDeleteOrderByAnoDesc(member, DeleteStatus.FALSE, pageable);
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

    // 관리자페이지 문의글 리스트 출력
    public Page<AskBoard> askBoards(AskBoardSearchDto searchDto, Pageable pageable) {
        Page<AskBoard> askBoards;

        switch (searchDto.getOrderBy()){
            case "completion": askBoards = askBoardInterfaceRepository.findDistinctByAskBoardRepliesIsNotNull(pageable);
                break;
            case "incomplete": askBoards = askBoardInterfaceRepository.findDistinctByAskBoardRepliesIsNull(pageable);
                break;
            default: askBoards = askBoardInterfaceRepository.
                    findByAskSubjectContainingOrAskContentContainingOrMemberNicknameContaining
                            (searchDto.getKeyword(), searchDto.getKeyword(), searchDto.getKeyword(), pageable);
                break;
        }

        return askBoards;
    }

    // 관리자 페이지 문의글 디테일
    public AskBoardRespDto getAskBoard(Long ano) {
        AskBoard askBoard = askBoardInterfaceRepository.findById(ano).orElse(null);
        if(askBoard != null){
            return new AskBoardRespDto().toDto(askBoard);
        }else{
            throw new NullPointerException("해당 문의글 데이터가 없습니다 id = " + ano);
        }
    }

    // 문의글 댓글 Desc
    public Map<String, Object> getAskBoardReplyDesc(Long ano) {
        Map<String, Object> resultMap = new HashMap<>();
        Optional<AskBoard> askBoardOP = askBoardInterfaceRepository.findById(ano);
        if(askBoardOP.isPresent()){
            AskBoard askBoardPS = askBoardOP.get();
            List<AskBoardReply> boardReplyList = askBoardReplyInterfaceRepository.findByAskBoardOrderByArnoDesc(askBoardPS);

            resultMap.put("boardReplyList", boardReplyList);
            resultMap.put("askBoard", new AskBoardRespDto().toDto(askBoardPS));

            return resultMap;
        } else{
            throw new NullPointerException("해당 문의글 데이터가 없습니다 id = " + ano);
        }


    }
}
