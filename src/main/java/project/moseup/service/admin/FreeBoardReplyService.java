package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.FreeBoardReply;
import project.moseup.domain.Member;
import project.moseup.dto.FreeBoardReplySaveDto;
import project.moseup.exception.MemberNotFoundException;
import project.moseup.repository.admin.AdminFreeBoardReplyRepository;
import project.moseup.repository.admin.AdminFreeBoardRepository;
import project.moseup.repository.admin.AdminMemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FreeBoardReplyService {

    private final AdminFreeBoardReplyRepository adminFreeBoardReplyRepository;
    private final AdminMemberRepository adminMemberRepository;
    private final AdminFreeBoardRepository adminFreeBoardRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, Object> delete(Long id) {
        Map<String, Object> map = new HashMap<>();
        Optional<FreeBoardReply> freeBoardReplyOP = adminFreeBoardReplyRepository.findById(id);

        if(freeBoardReplyOP.isPresent()){
            FreeBoardReply freeBoardReplyPS = freeBoardReplyOP.get();
            freeBoardReplyPS.deleteUpdate();

            map.put("fno", freeBoardReplyPS.getFreeBoard().getFno());
            return map;
        }else{
            throw new NullPointerException("댓글이 없습니다 id = " + id);
        }
    }


    @Transactional(rollbackFor = RuntimeException.class)
    public void save(FreeBoardReplySaveDto replySaveDto) {
        Member member = adminMemberRepository.findById(replySaveDto.getMno()).
                orElseThrow(() -> new MemberNotFoundException(replySaveDto.getMno()));
        FreeBoard freeBoard = adminFreeBoardRepository.findById(replySaveDto.getFno()).orElse(null);

        if(member != null && freeBoard != null){
            replySaveDto.setMember(member);
            replySaveDto.setFreeBoard(freeBoard);

            adminFreeBoardReplyRepository.save(replySaveDto.toEntity());
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, Object> reCover(Long id) {
        Map<String, Object> map = new HashMap<>();
        Optional<FreeBoardReply> freeBoardReplyOP = adminFreeBoardReplyRepository.findById(id);

        if(freeBoardReplyOP.isPresent()){
            FreeBoardReply freeBoardReplyPS = freeBoardReplyOP.get();
            freeBoardReplyPS.deleteRecover();

            map.put("fno", freeBoardReplyPS.getFreeBoard().getFno());
            return map;
        }else{
            throw new NullPointerException("댓글이 없습니다 id = " + id);
        }
    }
}
