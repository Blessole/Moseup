package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FreeBoardReply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "free_replyno")
	private Long frno;					//자게 댓글 번호

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "free_no")
	private FreeBoard freeBoard;		//게시물 번호

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@NotEmpty
	@Column(name = "free_replycontent")
	private String freeReplyContent;				//댓글 내용

	@Column(name = "free_replydate")
	private LocalDateTime freeReplyDate;			//댓글 작성일

	@Column(name = "free_replystep")
	private int step;					//댓글 순서

	@Column(name = "free_replylevel")
	private int level;					//댓글 깊이

	@Enumerated(EnumType.STRING)
	private DeleteStatus freeReplyDelete;		//댓글 삭제 여부


	@Builder
	public FreeBoardReply(FreeBoard freeBoard, Member member, String freeReplyContent, LocalDateTime freeReplyDate, int step, int level, DeleteStatus freeReplyDelete) {
		this.freeBoard = freeBoard;
		this.member = member;
		this.freeReplyContent = freeReplyContent;
		this.freeReplyDate = freeReplyDate;
		this.step = step;
		this.level = level;
		this.freeReplyDelete = freeReplyDelete;
	}
}
