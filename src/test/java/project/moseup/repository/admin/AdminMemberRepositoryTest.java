package project.moseup.repository.admin;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

//DB 관련 테스트
//DB와 관련된 컴포넌트만 메모리에 로딩
@DataJpaTest
public class AdminMemberRepositoryTest {

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    // 1. 회원 등록
    @Test
    public void memberSave_test(){
        System.out.println("회원등록_test 실행");
        // given (데이터 준비)
        Member member = new Member();
        member.setEmail("123@k1.com");
        member.setPassword("1234");
        member.setNickname("찬우");
        member.setName("정찬우");
        member.setGender(MemberGender.MALE);
        member.setAddress("안양");
        member.setPhoto("jpg");
        member.setPhone("010-3333");
        member.setMemberDate(LocalDateTime.now());
        member.setMemberDelete(DeleteStatus.FALSE);

        // when (테스트 실행)
        Member saveMember = adminMemberRepository.save(member);

        // then (검증)
        assertEquals("123@k1.com", saveMember.getEmail());
        assertEquals("1234", saveMember.getPassword());
        assertEquals("찬우", saveMember.getNickname());
        assertEquals("정찬우", saveMember.getName());
        assertEquals("안양", saveMember.getAddress());
        assertEquals("jpa", saveMember.getPhoto());
        assertEquals("010-3333", saveMember.getPhone());

    }

    // 2. 회원 리스트 출력

    // 3. 회원 한 명 출력

    // 4. 회원 정보 수정

    // 5. 회원 삭제


}