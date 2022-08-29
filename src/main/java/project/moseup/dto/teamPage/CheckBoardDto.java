package project.moseup.dto.teamPage;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

@Getter @Setter
public class CheckBoardDto {

    private Member member;

    private Team team;

    private LocalDateTime checkDate;

    private String checkContent;

    private String checkPhoto;

    private int checkLike;

    private int checkReadCount;
    
    public CheckBoard toEntity() {
    	return CheckBoard.createCheckBoard()
    			.member(member)
    			.team(team)
    			.checkDate(LocalDateTime.now())
    			.checkContent(checkContent)
    			.checkPhoto(checkPhoto)
    			.checkLike(0)
    			.checkReadCount(0).build();
    }
    
}
