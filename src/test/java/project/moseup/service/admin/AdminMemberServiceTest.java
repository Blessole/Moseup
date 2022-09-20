package project.moseup.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Role;
import project.moseup.dto.MemberRespDto;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class) //가짜 메모리 환경 만들기
public class AdminMemberServiceTest {

    @InjectMocks
    private AdminMemberService adminMemberService;

    @Mock //가짜 메모리 환경에 띄우기 (인터페이스 = 익명클래스가 뜸)
    private AdminMemberRepository adminMemberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

//    @Mock
//    private AdminBankbookRepository adminBankbookRepository;

    // 문제점 -> 서비스만 테스트하고 싶은데, 레포지토리 레이어가 함께 테스트 된다는 점
    @Test
    public void 회원등록() {
        // given
        MemberSaveReqDto dto = new MemberSaveReqDto();
        dto.setAddress2("");
        dto.setPhoto("");
        dto.setAddress("안양");
        dto.setMemberDate(LocalDate.now());
        dto.setMemberDelete(DeleteStatus.FALSE);
        dto.setEmail("5093@k.com");
        dto.setGender(MemberGender.FEMALE);
        dto.setName("서비스테스트");
        dto.setNickname("서비스테스트");
        dto.setPassword("a123123");
        dto.setPhone("01011111234");
        dto.setRole(Role.USER);

//        Bankbook bankbook = Bankbook.builder()
//                .member(dto.toEntity())
//                .bankbookDate(dto.getMemberDate())
//                .bankbookDeposit(0)
//                .bankbookTotal(0)
//                .bankbookWithdraw(0)
//                .dealList("굿모닝^^")
//                .build();
//        adminBankbookRepository.save(bankbook);

        // stub (가설)
        // any() 아무 데이터가 들어간다는 의미
        when(adminMemberRepository.save(any())).thenReturn(dto.toEntity());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(dto.getPassword());
//        when(adminBankbookRepository.save(any())).thenReturn(bankbook);

        // when
        MemberRespDto memberRespDto = adminMemberService.joinMember(dto);

        // then / assertThat 사용하기! (읽기 좋음)
        assertThat(dto.getNickname()).isEqualTo(memberRespDto.getNickname());
        //assertEquals(dto.getNickname(), memberRespDto.getNickname());
    }

    @Test
    public void 회원목록(){
        // given
        Member dto1 = Member.builder()
                .photo("")
                .address("안양")
                .memberDate(LocalDate.now())
                .memberDelete(DeleteStatus.FALSE)
                .email("5093@k.com")
                .gender(MemberGender.FEMALE)
                .name("서비스테스트")
                .nickname("서비스테스트")
                .password("a123123")
                .phone("01011111234")
                .role(Role.USER)
                .build();
        Member dto2 = Member.builder()
                .photo("")
                .address("경기도")
                .memberDate(LocalDate.now())
                .memberDelete(DeleteStatus.FALSE)
                .email("50933@k.com")
                .gender(MemberGender.FEMALE)
                .name("서비스테스트")
                .nickname("서비스테스트")
                .password("a123123")
                .phone("01011111234")
                .role(Role.USER)
                .build();

        // stub (가설)
        List<Member> memberList = new ArrayList<>();
        memberList.add(dto1);
        memberList.add(dto2);

        when(adminMemberRepository.findAll()).thenReturn(memberList);

        // when
        List<MemberRespDto> dtos = adminMemberService.memberFindAll();

        //print
        dtos.stream().forEach((dto) -> {
            System.out.println("=======테스트");
            System.out.println(dto.getMno());
            System.out.println(dto.getEmail());
        });

        // then
        assertThat(dtos.get(0).getAddress()).isEqualTo("안양");
        assertThat(dtos.get(0).getEmail()).isEqualTo("5093@k.com");

        assertThat(dtos.get(1).getAddress()).isEqualTo("경기도");
        assertThat(dtos.get(1).getEmail()).isEqualTo("50933@k.com");
    }

    @Test
    public void 회원한명조회_테스트(){
        // given
        Long id = 43L;

        // stub
        Member member = new Member(
                "777@777.com",
                "a123123",
                "조회테스트",
                "조회테스트",
                MemberGender.FEMALE,
                "주소",
                "01033333333",
                "NULL",
                DeleteStatus.FALSE,
                LocalDate.now(),
                Role.USER,
                "로그인타입");
        Optional<Member> memberOP = Optional.of(member);
        when(adminMemberRepository.findById(id)).thenReturn(memberOP);

        // when
        MemberRespDto memberRespDto = adminMemberService.memberFindBy(id);

        // then
        log.info(member.getEmail());
        log.info(member.getName());
        log.info("========================");
        log.info(memberRespDto.getEmail());
        log.info(memberRespDto.getName());

        assertThat(memberRespDto.getEmail()).isEqualTo(member.getEmail());
        assertThat(memberRespDto.getName()).isEqualTo(member.getName());

    }



    @Test
    public void 회원수정테스트(){
        // given (수정 데이터)
        Long id = 43L;
        MemberSaveReqDto dto = new MemberSaveReqDto();
        dto.setAddress("dd");
        dto.setPhone("01022222222");
        dto.setPhoto("null");
        dto.setGender(MemberGender.FEMALE);
        dto.setRole(Role.USER);
        dto.setEmail("111@111.com");
        dto.setPassword("a123123");
        dto.setNickname("테스트1234");
        dto.setMemberDate(LocalDate.now());
        dto.setMemberDelete(DeleteStatus.FALSE);
        dto.setName("테스트용용");

        // stub (가설로 db에 있는 데이터 정의)
        Member member = new Member(
                "777@777.com",
                "a123123",
                "조회테스트",
                "조회테스트",
                MemberGender.FEMALE,
                "주소",
                "01033333333",
                "NULL",
                DeleteStatus.FALSE,
                LocalDate.now(),
                Role.USER,
                "로그인타입");
        Optional<Member> memberOP = Optional.of(member);
        when(adminMemberRepository.findById(id)).thenReturn(memberOP);

        // when
        MemberRespDto memberRespDto = adminMemberService.memberUpdate(id, dto);

        // then
        assertThat(memberRespDto.getName()).isEqualTo(dto.getName());
        assertThat(memberRespDto.getNickname()).isEqualTo(dto.getNickname());
        assertThat(memberRespDto.getPhone()).isEqualTo(dto.getPhone());
        assertThat(memberRespDto.getPhoto()).isEqualTo(dto.getPhoto());





    }
}