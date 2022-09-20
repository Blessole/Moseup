package project.moseup.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "team_bankbook_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private LocalDateTime teamBankbookDate;

    @Builder
    public TeamBankbookDetail(TeamBankbook teamBankbook, Member member, String dealList, int teamBankbookDeposit, int teamBankbookWithdraw, int teamBankbookTotal, LocalDateTime teamBankbookDate) {
        this.teamBankbook = teamBankbook;
        this.member = member;
        this.dealList = dealList;
        this.teamBankbookDeposit = teamBankbookDeposit;
        this.teamBankbookWithdraw = teamBankbookWithdraw;
        this.teamBankbookTotal = teamBankbookTotal;
        this.teamBankbookDate = teamBankbookDate;
    }
}
