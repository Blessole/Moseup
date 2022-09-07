package project.moseup.repository.admin;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminBankbookRepositoryTest {



//    @Autowired
//    AdminMemberRepository adminMemberRepository;
//
//    @Test
//    public void 통장생성_Test(){
//        Member member = adminMemberRepository.findById(35L).orElse(null);
//
//        BankbookSaveReqDto bankbookSaveReqDto = BankbookSaveReqDto.builder()
//                .bankbookDate(LocalDateTime.now())
//                .bankbookTotal(0)
//                .bankbookDeposit(0)
//                .bankbookWithdraw(0)
//                .dealList("거래리스트를 문자열 하나로? 흠")
//                .member(member)
//                .build();
//        Bankbook bankbook = adminBankbookRepository.save(bankbookSaveReqDto.toEntity());
//
//        BankbookRespDto bankbookRespDto = new BankbookRespDto().toDto(bankbook);
//
//        assertEquals(bankbookRespDto.getMember().getMno(), member.getMno());
//
//
//    }
}