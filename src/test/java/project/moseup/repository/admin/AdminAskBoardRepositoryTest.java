package project.moseup.repository.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardSaveReqDto;

import java.time.LocalDateTime;

@SpringBootTest
public class AdminAskBoardRepositoryTest {

    @Autowired
    AdminAskBoardRepository adminAskBoardRepository;
    @Autowired
    AdminMemberRepository adminMemberRepository;

    @Test
    public void 문의글생성(){
        Member member = adminMemberRepository.findById(47L).orElse(null);
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

}