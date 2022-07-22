package project.moseup.repository.admin;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.moseup.repository.MemberRepository;

//DB 관련 테스트
//DB와 관련된 컴포넌트만 메모리에 로딩
@DataJpaTest
public class AdminMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    // 1. 회원 등록
    @Test
    public void 회원등록_test(){
        System.out.println("회원등록_test 실행");
    }

    // 2. 회원 리스트 출력

    // 3. 회원 한 명 출력

    // 4. 회원 정보 수정

    // 5. 회원 삭제


}