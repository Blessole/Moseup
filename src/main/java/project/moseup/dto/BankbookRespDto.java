package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.Bankbook;
import project.moseup.domain.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BankbookRespDto {

    // 통장 번호
    private Long dno;

    // 회원 번호
    private Member member;

    // 거래 리스트
    private String dealList;

    // 입금액
    private int bankbookDeposit;

    // 출금액
    private int bankbookWithdraw;
    // 총액
    private int bankbookTotal;

    // 거래(입출금) 일자
    private LocalDateTime bankbookDate;


    public BankbookRespDto toDto(Bankbook bankbookPS){
        this.dno = bankbookPS.getDno();
        this.member = bankbookPS.getMember();
        this.dealList = bankbookPS.getDealList();
        this.bankbookDeposit = bankbookPS.getBankbookDeposit();
        this.bankbookWithdraw = bankbookPS.getBankbookWithdraw();
        this.bankbookTotal = bankbookPS.getBankbookTotal();
        this.bankbookDate = bankbookPS.getBankbookDate();
        return this;
    }
}
