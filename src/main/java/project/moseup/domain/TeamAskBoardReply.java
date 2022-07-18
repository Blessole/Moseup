package project.moseup.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class TeamAskBoardReply {

	@Column(name = "team_askreplyno")
    @GeneratedValue
    @Id
    private Long tarno;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_askno")
    private TeamAskBoard teamAskBoard;
    
    @Column(name = "team_askreplycontent")
    @NotEmpty
    private String teamAskReplyContent;

    @Column(name = "team_askreplydate")
    private LocalDateTime teamAskReplyDate;
    
    @Column(name = "team_askreplydelete")
    @Enumerated(EnumType.STRING)
    private DeleteStatus teamAskReplyDelete;

}
