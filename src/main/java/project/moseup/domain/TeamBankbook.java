package project.moseup.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "team_bankbook")
public class TeamBankbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_bankbook_no") // 통장 번호
    private Long tbno;

    @OneToOne
    @JoinColumn(name = "team_no")
    @JsonIgnore
    private Team team;

    @OneToMany(mappedBy = "teamBankbook")
    private List<TeamBankbookDetail> teamBankbookDetails = new ArrayList<>();

    @Builder
    public TeamBankbook(Team team) {
        this.team = team;
    }

}
