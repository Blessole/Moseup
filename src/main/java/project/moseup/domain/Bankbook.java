package project.moseup.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
@Table(name = "member_bankbooks")
public class Bankbook {

	@Id @GeneratedValue
	@Column(name = "bankbook_dealno")
	private Long dno;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@Column(name = "bankbook_deallist")
	private String dealList;

	@Column(name = "bankbook_deposit")
	private int bankbookDeposit;

	@Column(name = "bankbook_withdraw")
	private int bankbookWithdraw;

	@Column(name = "bankbook_total")
	private int bankbookTotal;

	@Column(name = "bankbook_date")
	private LocalDateTime bankbookDate;
}
