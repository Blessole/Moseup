package project.moseup.repository.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.Member;
import project.moseup.dto.FreeBoardSaveReqDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdminFreeBoardRepositoryTest {

    @Autowired
    AdminFreeBoardRepository adminFreeBoardRepository;

    @Autowired
    AdminMemberRepository adminMemberRepository;

    @Test
    public void 자유게시판글작성(){
        Member member = adminMemberRepository.findById(47L).orElse(null);

        FreeBoardSaveReqDto saveReqDto = FreeBoardSaveReqDto.builder()
                .member(member)
                .freeTitle("프리테스트2")
                .freeContent("프리테스트2")
                .build();
        FreeBoard freeBoardPS = adminFreeBoardRepository.save(saveReqDto.toEntity());

        assertThat(freeBoardPS.getFreeTitle()).isEqualTo(saveReqDto.getFreeTitle());
        assertThat(freeBoardPS.getFreeContent()).isEqualTo(saveReqDto.getFreeContent());

    }
}