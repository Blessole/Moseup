package project.moseup.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
@Table(name = "member_bankbooks")
public class BankBook {

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
