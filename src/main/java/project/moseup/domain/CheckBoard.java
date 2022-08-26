package project.moseup.domain;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckBoard {

	@Column(name = "check_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        // 안전한 객체 생성 패턴 = 필요한 값이 없는 경우에 NULL 예외가 발생해 메시지를 보여주고 흐름 종료
        Assert.hasText(String.valueOf(member), "멤버는 [NULL]이 될 수 없습니다");
        Assert.hasText(String.valueOf(team), "팀은 [NULL]이 될 수 없습니다");
        Assert.hasText(String.valueOf(checkDate), "날짜는 [NULL]이 될 수 없습니다");
        Assert.hasText(checkPhoto, "사진은 [NULL]이 될 수 없습니다");
        Assert.hasText(String.valueOf(checkReadCount), "전화번호는 [NULL]이 될 수 없습니다");

        this.member = member;
    	this.team = team;
    	this.checkDate = checkDate;
    	this.checkContent = checkContent;
    	this.checkPhoto = checkPhoto;
    	this.checkLike = checkLike;
    	this.checkReadCount = checkReadCount;
    }

}
