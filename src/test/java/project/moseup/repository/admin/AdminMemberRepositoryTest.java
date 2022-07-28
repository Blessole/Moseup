package project.moseup.repository.admin;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// 클라이언트 -> 요청 -> Controller(3) 클라이언트와 테스트 -> Service(2) 기능들이 트랜잭션을 잘 타는지 테스트 -> Repository(1) DB 관련 테스트
// 여긴 DB 관련 테스트
// Controller Member 데이터  -> Dto -> Service -> Member 엔티티 변환 -> MemberRepository.save(Member)

// DB와 관련된 컴포넌트만 메모리에 로딩
@SpringBootTest
@Transactional //테스트 메서드가 하나 실행 후 종료되면 데이터가 초기화된다. 1건 > 2건 > 트랜잭션 종료 > 데이터 초기화 ***primary key auto_increment 값은 초기화가 안 됨
public class AdminMemberRepositoryTest {

    @Autowired
    AdminMemberRepository adminMemberRepository;

    // 1. 회원 등록
    @Order(1)
    @Test //순서 보장이 안 됨
    public void memberSave_test(){
        // given (데이터 준비)
        Member member = Member.builder()
                .email("123@k1.com")
                .password("1234")
                .nickname("찬우")
                .name("정찬우")
                .gender(MemberGender.MALE)
                .address("안양")
                .photo("jpg")
                .phone("010-3333")
                .memberDate(LocalDateTime.now())
                .memberDelete(DeleteStatus.FALSE)
                .build();

        // when (테스트 실행)
        Member MemberPS = adminMemberRepository.save(member); //save(member) 클라이언트에게 받은 데이터
        // MemberPS =  save 메소드가 db에 저장된 Member 를 return(DB 데이터와 동기화된 데이터)
        // PS = persistence(영속성) -> 영구적으로 저장된 데이터 == DB에 저장된 데이터

        // then (검증)
        assertEquals("123@k1.com", MemberPS.getEmail());
        assertEquals("1234", MemberPS.getPassword());
        assertEquals("찬우", MemberPS.getNickname());
        assertEquals("정찬우", MemberPS.getName());
        assertEquals("안양", MemberPS.getAddress());
        assertEquals("jpg", MemberPS.getPhoto());
        assertEquals("010-3333", MemberPS.getPhone());

    }

    // 2. 회원 목록 출력
    @Order(2)
    @Test
    public void memberList_test(){
        // given

        // when
        List<Member> membersPS = adminMemberRepository.findAll();

        // then
        System.out.println(membersPS.size());
        assertEquals("123@k1.com", membersPS.get(0).getEmail());
        assertEquals("1234", membersPS.get(0).getPassword());
    }

    // 3. 회원 한 명 출력
    @Order(3)
    @Test
    public void memberOne_test(){
        // given

        // when
        Member memberOnePS = adminMemberRepository.findById(6L).get();
        System.out.println(memberOnePS.getAddress());
        System.out.println(memberOnePS.getMemberDate());
        // then
//        assertEquals("123@k1.com", memberOnePS.getEmail());
//        assertEquals("1234", memberOnePS.getPassword());

    }

    // 4. 회원 삭제
    @Order(4)
    @Test
    public void memberDelete_test(){
        // given

        // when
        adminMemberRepository.deleteById(1L);

        // then
        assertFalse(adminMemberRepository.findById(1L).isPresent()); // false 일 때 성공
    }

    // 5. 회원 수정


}