package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.Team;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CheckBoardRespDto {
    private Long cno;
    private Member member;
    private Team team;
    private LocalDateTime checkDate;
    private String checkContent;
    private String checkPhoto;
    private String realPhoto;   // 사진 이름만 불러오기용
    private int checkLike;
    private int checkReadCount;

    public CheckBoardRespDto toDto(CheckBoard checkBoardPS){
        this.cno = checkBoardPS.getCno();
        this.member = checkBoardPS.getMember();
        this.team = checkBoardPS.getTeam();
        this.checkDate = checkBoardPS.getCheckDate();
        this.checkContent = checkBoardPS.getCheckContent();
        this.checkLike = checkBoardPS.getCheckLike();
        this.checkReadCount = checkBoardPS.getCheckReadCount();
        this.checkPhoto = checkBoardPS.getCheckPhoto();
        
        // DB 사진 경로 - 내부 경로로 자르기
        String originPhoto = checkBoardPS.getCheckPhoto();
        int index = originPhoto.indexOf("images");
        String realName = originPhoto.substring(index - 1);
        this.realPhoto = realName;

        return this;
    }

    public String getPhotoViewPath(){
        int index = this.checkPhoto.indexOf("images");
        return this.checkPhoto.substring(index - 1);
    }


}
