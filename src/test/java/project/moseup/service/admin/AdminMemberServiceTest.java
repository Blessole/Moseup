package project.moseup.service.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.moseup.domain.*;
import project.moseup.dto.MemberRespDto;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminBankbookRepository;
import project.moseup.repository.admin.AdminMemberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class) //가짜 메모리 환경 만들기
public class AdminMemberServiceTest {

    @InjectMocks
    private AdminMemberService adminMemberService;

    @Mock //가짜 메모리 환경에 띄우기 (인터페이스 = 익명클래스가 뜸)
    private AdminMemberRepository adminMemberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AdminBankbookRepository adminBankbookRepository;

    // 문제점 -> 서비스만 테스트하고 싶은데, 레포지토리 레이어가 함께 테스트 된다는 점
    @Test
    public void 회원등록() {
        // given
        MemberSaveReqDto dto = new MemberSaveReqDto();
        dto.setAddress2("");
        dto.setPhoto("");
        dto.setAddress("안양");
        dto.setMemberDate(LocalDateTime.now());
        dto.setMemberDelete(DeleteStatus.FALSE);
        dto.setEmail("5093@k.com");
        dto.setGender(MemberGender.FEMALE);
        dto.setName("서비스테스트");
        dto.setNickname("서비스테스트");
        dto.setPassword("a123123");
        dto.setPhone("01011111234");
        dto.setRole(Role.USER);

        Bankbook bankbook = Bankbook.builder()
                .member(dto.toEntity())
                .bankbookDate(dto.getMemberDate())
                .bankbookDeposit(0)
                .bankbookTotal(0)
                .bankbookWithdraw(0)
                .dealList("굿모닝^^")
                .build();
        adminBankbookRepository.save(bankbook);

        // stub (가설)
        // any() 아무 데이터가 들어간다는 의미
        when(adminMemberRepository.save(any())).thenReturn(dto.toEntity());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(dto.getPassword());
        when(adminBankbookRepository.save(any())).thenReturn(bankbook);

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
                .memberDate(LocalDateTime.now())
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
                .memberDate(LocalDateTime.now())
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
        List<MemberRespDto> dtos = adminMemberService.회원목록보기();

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
}