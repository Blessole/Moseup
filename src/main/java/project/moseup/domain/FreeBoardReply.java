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
@Getter @Setter
public class FreeBoardReply {

	@Id @GeneratedValue
	@Column(name = "free_reply_no")
	private int frno;					//자게 댓글 번호

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "free_no")
	private FreeBoard freeboard;		//게시물 번호

	@Column(name = "member_no")
	private int mno;					//회원 번호

	@Column(name = "free_reply_content")
	private String content;				//댓글 내용

	@Column(name = "free_reply_date")
	private LocalDateTime date;			//댓글 작성일

	@Column(name = "free_reply_step")
	private int step;					//댓글 순서

	@Column(name = "free_reply_level")
	private int level;					//댓글 깊이

	@NotEmpty
	@Enumerated(EnumType.STRING)
	private DeleteStatus delete;		//댓글 삭제 여부
}
