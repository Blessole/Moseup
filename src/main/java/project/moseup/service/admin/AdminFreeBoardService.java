package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.FreeBoardReply;
import project.moseup.dto.FreeBoardRespDto;
import project.moseup.repository.admin.AdminFreeBoardReplyRepository;
import project.moseup.repository.admin.AdminFreeBoardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminFreeBoardService {

    private final AdminFreeBoardRepository adminFreeBoardRepository;
    private final AdminFreeBoardReplyRepository adminFreeBoardReplyRepository;

    public Page<FreeBoard> freeBoards(String keyword, Pageable pageable) {
        return adminFreeBoardRepository.
                findByFreeTitleContainingOrFreeContentContainingOrMemberNicknameContaining
                        (keyword, keyword, keyword, pageable);
    }

    public Map<String, Object> freeBoardDetail(Long fno) {
        Map<String, Object> map = new HashMap<>();
        Optional<FreeBoard> freeBoardOP = adminFreeBoardRepository.findById(fno);

        if(freeBoardOP.isPresent()){
            FreeBoard freeBoardPS = freeBoardOP.get();
            List<FreeBoardReply> boardReplies =
                    adminFreeBoardReplyRepository.findByFreeBoardOrderByFrnoDesc(freeBoardPS);
            FreeBoardRespDto freeBoard = new FreeBoardRespDto().toDto(freeBoardPS);

            map.put("boardReplies", boardReplies);
            map.put("deleteTrue", DeleteStatus.TRUE);
            map.put("freeBoard", freeBoard);
            return map;
        }else{
            throw new IllegalStateException("찾고자 하는 게시글이 없습니다");
        }
    }


}
