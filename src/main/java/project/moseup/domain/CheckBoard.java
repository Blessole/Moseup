package project.moseup.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckBoard {

	@Column(name = "check_no")
    @GeneratedValue
    @Id
    private Long cno;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no")
    private Team team;

    @Column(name = "check_date")
    private LocalDateTime checkDate;

    @Column(name = "check_content")
    private String checkContent;

    @Column(name = "check_photo")
    @NotEmpty
    private String checkPhoto;

    @Column(name = "check_like")
    private int checkLike;

    @Column(name = "check_readcount")
    @NotNull
    private int checkReadCount;
    
    @Builder(builderClassName = "toEntity", builderMethodName = "createCheckBoard")
    public CheckBoard(Member member, Team team, LocalDateTime checkDate, String checkContent, String checkPhoto, int checkLike, int checkReadCount) {
    	this.member = member;
    	this.team = team;
    	this.checkDate = checkDate;
    	this.checkContent = checkContent;
    	this.checkPhoto = checkPhoto;
    	this.checkLike = checkLike;
    	this.checkReadCount = checkReadCount;
    }

}
