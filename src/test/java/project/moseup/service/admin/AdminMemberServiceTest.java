//package project.moseup.service.admin;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import project.moseup.domain.Member;
//import project.moseup.domain.MemberGender;
//import project.moseup.dto.MemberRespDto;
//import project.moseup.dto.MemberSaveReqDto;
//import project.moseup.repository.admin.AdminMemberRepository;
//
//import static org.junit.Assert.assertEquals;
//
//@SpringBootTest
//public class AdminMemberServiceTest {
//
//    @Autowired
//    AdminMemberService adminMemberService;
//    @Autowired
//    AdminMemberRepository adminMemberRepository;
//
//    @Test
//    void joinMember() {
//        MemberSaveReqDto saveReqDto = MemberSaveReqDto.builder()
//                .address("통장테스트")
//                .name("통장테스트")
//                .email("bankbookTest@t1.com")
//                .password("a123123")
//                .photo("")
//                .gender(MemberGender.FEMALE)
//                .nickname("통장테스트")
//                .phone("01033333333")
//                .build();
//
//        MemberRespDto memberPS = adminMemberService.joinMember(saveReqDto);
//        Member member = adminMemberRepository.findById(memberPS.getMno()).orElse(null);
//
//        assertEquals(member.getBankbook().getDealList(), "굿모닝^^");
//    }
//}