package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class AskBoardReply {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
		Assert.notNull(askBoard, "askBoard은 [NULL]이 될 수 없습니다");
		Assert.notNull(member, "member는 [NULL]이 될 수 없습니다");
		Assert.hasText(askReplyContent, "askReplyContent은 [NULL]이 될 수 없습니다");
		Assert.notNull(askReplyDate, "askReplyDate은 [NULL]이 될 수 없습니다");
		Assert.notNull(askReplyDelete, "askReplyDelete은 [NULL]이 될 수 없습니다");

		this.askBoard = askBoard;
		this.member = member;
		this.askReplyContent = askReplyContent;
		this.askReplyDate = askReplyDate;
		this.askReplyDelete = askReplyDelete;
	}





}
