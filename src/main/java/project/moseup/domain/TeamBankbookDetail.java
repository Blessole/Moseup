package project.moseup.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "team_bankbook_detail")
public class TeamBankbookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_bankbook_dealno") // 내역 번호
    private Long tdno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_bankbook_no")
    private TeamBankbook teamBankbook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "team_bankbook_deallist") // 거래 리스트
    private String dealList;

    @Column(name = "team_bankbook_deposit") // 입금액
    private int teamBankbookDeposit;

    @Column(name = "team_bankbook_withdraw") // 출금액
    private int teamBankbookWithdraw;

    @Column(name = "team_bankbook_total") // 총액
    private int teamBankbookTotal;

    @Column(name = "team_bankbook_date") // 거래일
    LocalDateTime teamBankbookDate;







}
