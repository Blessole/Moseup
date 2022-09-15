package project.moseup.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AskBoardReply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Builder
	public AskBoardReply(AskBoard askBoard, Member member, String askReplyContent, LocalDateTime askReplyDate, DeleteStatus askReplyDelete) {
		Assert.hasText(String.valueOf(member), "멤버는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(askBoard), "AskBoard는 [NULL]이 될 수 없습니다");
		Assert.hasText(askReplyContent, "내용은 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(askReplyDelete), "삭제여부는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(askReplyDate), "댓글 작성일은 [NULL]이 될 수 없습니다");

		this.askBoard = askBoard;
		this.member = member;
		this.askReplyContent = askReplyContent;
		this.askReplyDate = askReplyDate;
		this.askReplyDelete = askReplyDelete;
	}

	public void replyDelete(){
		this.askReplyDelete = DeleteStatus.TRUE;
	}
	public void replyRecover(){
		this.askReplyDelete = DeleteStatus.FALSE;
	}
}
