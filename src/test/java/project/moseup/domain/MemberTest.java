package project.moseup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberTest {

    @Test
    public void Member_email_비어있으면_예외발생(){
        Member.builder()
                .email("")
                .photo("dd")
                .name("dd")
                .nickname("dd")
                .password("1234")
                .phone("3333")
                .memberDate(LocalDateTime.now())
                .memberDelete(DeleteStatus.FALSE)
                .gender(MemberGender.MALE)
                .address("123")
                .build();
        // 출력 기대값 : 이메일은 [NULL]이 될 수 없습니다
    }

}