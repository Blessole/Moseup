package project.moseup.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.Member;
import project.moseup.domain.TeamBankbook;
import project.moseup.domain.TeamBankbookDetail;

@Getter
@Setter
public class TeamBankbookDetailWithdrawDto {
	
	private TeamBankbook teamBankbook;

    private Member member;

    private String dealList;	// 거래 리스트
    
    private int teamBankbookDeposit;// 입금액

    private int teamBankbookWithdraw;// 출금액

    private int teamBankbookTotal;// 총액

    private LocalDateTime teamBankbookDate;// 거래일

	public TeamBankbookDetail toEntity() {
		return TeamBankbookDetail.builder()
				.teamBankbook(teamBankbook)
				.member(member)
				.dealList(dealList)
				.teamBankbookDeposit(teamBankbookDeposit)
				.teamBankbookWithdraw(teamBankbookWithdraw)
				.teamBankbookTotal(teamBankbookTotal)
				.teamBankbookDate(teamBankbookDate)
				.build();
	}
    

}
