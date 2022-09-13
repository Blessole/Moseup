package project.moseup.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "team_bankbook")
public class TeamBankbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_bankbook_no") // 통장 번호
    private Long tbno;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no")
    private Team team;

    @OneToMany(mappedBy = "teamBankbook")
    private List<TeamBankbookDetail> teamBankbookDetails = new ArrayList<>();



}
