package project.moseup.repository.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.FreeBoardReply;
import project.moseup.domain.Member;
import project.moseup.dto.FreeBoardSaveReqDto;
import project.moseup.exception.MemberNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class AdminFreeBoardRepositoryTest {

    @Autowired
    AdminFreeBoardRepository adminFreeBoardRepository;

    @Autowired
    AdminMemberRepository adminMemberRepository;

    @Autowired
    AdminFreeBoardReplyRepository adminFreeBoardReplyRepository;

    @Test
    public void 자유게시판글작성(){
        Member member = adminMemberRepository.findById(1L).orElseThrow(()-> new MemberNotFoundException(1L));

        FreeBoardSaveReqDto saveReqDto = FreeBoardSaveReqDto.builder()
                .member(member)
                .freeTitle("프리테스트2")
                .freeContent("프리테스트2")
                .build();
        FreeBoard freeBoardPS = adminFreeBoardRepository.save(saveReqDto.toEntity());

        assertThat(freeBoardPS.getFreeTitle()).isEqualTo(saveReqDto.getFreeTitle());
        assertThat(freeBoardPS.getFreeContent()).isEqualTo(saveReqDto.getFreeContent());

    }

    @Test
    public void 게시판댓글작성(){
        Member member = adminMemberRepository.findById(2L).orElseThrow(()-> new MemberNotFoundException(2L));

        Optional<FreeBoard> freeBoardOP = adminFreeBoardRepository.findById(1L);
        FreeBoard freeBoardPS = null;
        FreeBoardReply freeBoardReply = null;
        FreeBoardReply freeBoardReplyPS = null;

        if(freeBoardOP.isPresent()){
            freeBoardPS = freeBoardOP.get();

            freeBoardReply = FreeBoardReply.builder()
                    .freeBoard(freeBoardPS)
                    .freeReplyContent("댓글테스트")
                    .freeReplyDate(LocalDateTime.now())
                    .freeReplyDelete(DeleteStatus.FALSE)
                    .member(member)
                    .level(1)
                    .step(1)
                    .build();
            freeBoardReplyPS = adminFreeBoardReplyRepository.save(freeBoardReply);

        }else{
            throw new RuntimeException("데이터가 없잖아");
        }

        assertThat(freeBoardPS.getFreeBoardReplies().get(0).getFreeReplyContent()).isEqualTo(freeBoardReplyPS.getFreeReplyContent());
    }



}