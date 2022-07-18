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
public class AskBoardReply {

	@Id @GeneratedValue
	@Column(name = "ask_replyno")
	private Long arno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ask_no")
	private AskBoard askBoard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;
	
	@NotEmpty
	@Column(name = "ask_replycontent")
	private String askReplyContent;
	
	@Column(name = "ask_replydate")
	private LocalDateTime askReplyDate;
	
	@Column(name = "ask_replydelete")
	@Enumerated(EnumType.STRING)
	private DeleteStatus askReplyDelete;
}
