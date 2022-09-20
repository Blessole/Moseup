package project.moseup.repository.admin;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.MemberSaveReqDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// 클라이언트 -> 요청 -> Controller(3) 클라이언트와 테스트 -> Service(2) 기능들이 트랜잭션을 잘 타는지 테스트 -> Repository(1) DB 관련 테스트
// 여긴 DB 관련 테스트
// Controller Member 데이터  -> Dto -> Service -> Member 엔티티 변환 -> MemberRepository.save(Member)

// DB와 관련된 컴포넌트만 메모리에 로딩
@SpringBootTest
//@Transactional //테스트 메서드가 하나 실행 후 종료되면 데이터가 초기화된다. 1건 > 2건 > 트랜잭션 종료 > 데이터 초기화 ***primary key auto_increment 값은 초기화가 안 됨
public class AdminMemberRepositoryTest {

    @Autowired
    AdminMemberRepository adminMemberRepository;

    // 1. 회원 등록
    @Order(1)
    @Test //순서 보장이 안 됨
    public void memberSave_test(){
        // given (데이터 준비)
        MemberSaveReqDto member = MemberSaveReqDto.builder()
                .email("test@t0.com")
                .password("a123123")
                .nickname("자동처리테스트")
                .name("자동처리테스트")
                .gender(MemberGender.FEMALE)
                .address("통장맨")
                .photo("jpg")
                .phone("01033333333")
                .build();

        // when (테스트 실행)
        Member MemberPS = adminMemberRepository.save(member.toEntity()); //save(member) 클라이언트에게 받은 데이터
        // MemberPS =  save 메소드가 db에 저장된 Member 를 return(DB 데이터와 동기화된 데이터)
        // PS = persistence(영속성) -> 영구적으로 저장된 데이터 == DB에 저장된 데이터



        // then (검증)
        assertEquals("test@t0.com", MemberPS.getEmail());

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
        Member member1 = adminMemberRepository.findById(13L).orElse(null);

        // when
        adminMemberRepository.deleteById(member1.getMno());

        // then
        assertFalse(adminMemberRepository.findById(13L).isPresent()); // false 일 때 성공


    }

    // 5. 회원 수정
    @Test
    public void memberUpdate_test(){
        // given 데이터베이스에 있는 테스트 아이디 사용
        Member member = adminMemberRepository.findById(13L).orElse(null);

        member.deleteUpdate(DeleteStatus.TRUE);
        // when
        adminMemberRepository.save(member);
//        List<Member> members = adminMemberRepository.findAll().stream()
//                .filter(m -> m.getMemberDelete().equals(DeleteStatus.TRUE))
//                .collect(Collectors.toList());

//        for (Member member1 : members){
//            System.out.println(member1.getMno());
//        }

        // then
        assertEquals(DeleteStatus.TRUE, member.getMemberDelete());
        member.toString();

    }

    @Test
    public void 멤버조회_Test2(){
        Member member = adminMemberRepository.findById(35L).orElse(null);

        System.out.println(member);

        assertEquals("32z", member.getNickname());
    }


}