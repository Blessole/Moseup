package project.moseup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.Team;
import project.moseup.domain.TeamBankbook;

@Getter
@Setter
public class TeamBankBookReqDto {

	private Team team;

	@Builder
    public TeamBankBookReqDto(Team team) {
        this.team = team;
    }

	public TeamBankBookReqDto() {	}

	public TeamBankbook toEntity() {
		return TeamBankbook.builder()
				.team(team)
				.build();
	}
	


}