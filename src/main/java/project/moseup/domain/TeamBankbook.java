package project.moseup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Team team;

    @OneToMany(mappedBy = "teamBankbook")
    private List<TeamBankbookDetail> teamBankbookDetails = new ArrayList<>();

	@Builder
	private TeamBankbook(Team team) {
		this.team = team;
	}
	
}
