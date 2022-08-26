package project.moseup.domain;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
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

}
