package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.FreeBoard;
import project.moseup.dto.FreeBoardRespDto;
import project.moseup.repository.admin.AdminFreeBoardRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminFreeBoardService {

    private final AdminFreeBoardRepository adminFreeBoardRepository;

    public Page<FreeBoard> freeBoards(String keyword, Pageable pageable) {
        return adminFreeBoardRepository.findByFreeTitleContainingOrFreeContentContainingOrMemberNicknameContaining(keyword, keyword, keyword, pageable);
    }

    public FreeBoardRespDto freeBoardDetail(Long fno) {
        Optional<FreeBoard> freeBoardOP = adminFreeBoardRepository.findById(fno);
        if(freeBoardOP.isPresent()){
            FreeBoard freeBoardPS = freeBoardOP.get();
            return new FreeBoardRespDto().toDto(freeBoardPS);
        }else{
            throw new IllegalStateException("찾고자 하는 게시글이 없습니다");
        }
    }


}
