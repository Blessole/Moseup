package project.moseup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import project.moseup.domain.Bankbook;
import project.moseup.domain.Member;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class BankbookSaveReqDto {

    @NotEmpty(message = "회원 정보가 없습니다")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime bankbookDate;

    public BankbookSaveReqDto(){}

    @Builder
    public BankbookSaveReqDto(Member member, String dealList, int bankbookDeposit, int bankbookWithdraw, int bankbookTotal, LocalDateTime bankbookDate) {
        Assert.notNull(member, "멤버는 [NULL]이 될 수 없습니다");

        this.member = member;
        this.dealList = dealList;
        this.bankbookDeposit = bankbookDeposit;
        this.bankbookWithdraw = bankbookWithdraw;
        this.bankbookTotal = bankbookTotal;
        this.bankbookDate = bankbookDate;
    }

    public Bankbook toEntity(){
        return Bankbook.builder()
                .member(member)
                .dealList(dealList)
                .bankbookDeposit(bankbookDeposit)
                .bankbookDate(bankbookDate)
                .bankbookTotal(bankbookTotal)
                .bankbookWithdraw(bankbookWithdraw)
                .build();
    }
}
