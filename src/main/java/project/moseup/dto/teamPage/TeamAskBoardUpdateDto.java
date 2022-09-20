package project.moseup.dto.teamPage;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.SecretStatus;

@Getter @Setter
public class TeamAskBoardUpdateDto {

	private String teamAskSubject;

	private String teamAskContent;

	private SecretStatus secret;

}
