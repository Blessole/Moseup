package project.moseup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member_bankbooks")
public class Bankbook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bankbook_dealno") // 통장 번호
	private Long dno;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no") // 회원 번호
	private Member member;

	@Column(name = "bankbook_deallist") // 거래 리스트
	private String dealList;

	@Column(name = "bankbook_deposit") // 입금액
	private int bankbookDeposit;

	@Column(name = "bankbook_withdraw") // 출금액
	private int bankbookWithdraw;

	@Column(name = "bankbook_total") // 총액
	private int bankbookTotal;

	@Column(name = "bankbook_date") // 거래(입출금) 일자
	private LocalDateTime bankbookDate;

	@Builder
	public Bankbook(Member member, String dealList, int bankbookDeposit, int bankbookWithdraw, int bankbookTotal, LocalDateTime bankbookDate) {
		this.member = member;
		this.dealList = dealList;
		this.bankbookDeposit = bankbookDeposit;
		this.bankbookWithdraw = bankbookWithdraw;
		this.bankbookTotal = bankbookTotal;
		this.bankbookDate = bankbookDate;
	}
}
