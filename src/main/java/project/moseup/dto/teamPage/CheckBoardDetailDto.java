package project.moseup.dto.teamPage;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

@Getter @Setter
public class CheckBoardDetailDto {
	
	private Long cno;
	
	private Member member;

    private Team team;

    private LocalDateTime checkDate;

    private String checkContent;

    private String checkPhoto;
    
    private String realPhoto;

    private int checkLike;

    private int checkReadCount;
    
    public CheckBoardDetailDto toDto(CheckBoard checkBoard) {
    	this.cno = checkBoard.getCno();
    	this.member = checkBoard.getMember();
    	this.team = checkBoard.getTeam();
    	this.checkDate = checkBoard.getCheckDate();
    	this.checkReadCount = checkBoard.getCheckReadCount();
    	this.checkPhoto = checkBoard.getCheckPhoto();
    	this.checkLike = checkBoard.getCheckLike();
    	this.checkContent = checkBoard.getCheckContent();
    	
    	String originPhoto = checkBoard.getCheckPhoto();
        int index = originPhoto.indexOf("images");
        String realName = originPhoto.substring(index - 1);
        this.realPhoto = realName;

    	return this;
    }
}
