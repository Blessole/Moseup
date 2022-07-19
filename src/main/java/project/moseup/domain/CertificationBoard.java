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

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class CertificationBoard {

	@Column(name = "certi_no")
    @GeneratedValue
    @Id
    private Long cno;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member certificationBoardMember;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no")
    private Team team;

    @Column(name = "certi_date")
    private LocalDateTime certificationBoardDate;

    @Column(name = "certi_content")
    private String cContent;

    @Column(name = "certi_photo")
    @NotEmpty
    private String cphoto;

    @Column(name = "certi_like")
    @NotNull
    private int clike;

    @Column(name = "certi_readcount")
    @NotNull
    private int creadCount;


}
