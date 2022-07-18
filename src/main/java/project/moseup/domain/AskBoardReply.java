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

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class AskBoardReply {

	@Id @GeneratedValue
	@Column(name = "ask_reply_no")
	private Long arno;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ask_no")
	private AskBoard askboard;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;
	
	@NotEmpty
	@Column(name = "ask_reply_content")
	private int content;
	
	@NotEmpty
	@Column(name = "ask_reply_date")
	private LocalDateTime ardate;
	
	@NotEmpty
	@Column(name = "ask_reply_delete")
	private DeleteStatus ardelete;
}
