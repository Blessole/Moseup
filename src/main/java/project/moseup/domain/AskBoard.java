package project.moseup.domain;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AskBoard {

	@Id @GeneratedValue
	@Column(name = "ask_no")
	private Long ano;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member member;

	@NotEmpty
	@Column(name = "ask_subject")
	private String askSubject;

	@NotEmpty
	@Column(name = "ask_content")
	private String askContent;

	@Column(name = "ask_photo")
	private String askPhoto;

	@Column(name = "ask_date")
	private LocalDateTime askDate;

	@Enumerated(EnumType.STRING)
	private DeleteStatus askDelete;

	@Builder
	public AskBoard(Member member, String askSubject, String askContent, String askPhoto, LocalDateTime askDate, DeleteStatus askDelete){
		Assert.hasText(String.valueOf(member), "멤버는 [NULL]이 될 수 없습니다");
		Assert.hasText(askSubject, "제목은 [NULL]이 될 수 없습니다");
		Assert.hasText(askContent, "내용은 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(askDelete), "탈퇴여부는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(askDate), "회원생성일은 [NULL]이 될 수 없습니다");

		this.member = member;
		this.askSubject = askSubject;
		this.askContent = askContent;
		this.askPhoto = askPhoto;
		this.askDate = askDate;
		this.askDelete = askDelete;
	}

	// 연관관계 맵핑
	@OneToMany(mappedBy = "askBoard")
	private List<AskBoardReply> askBoardReplies = new ArrayList<>();

	public void update(String askSubject, String askContent, String askPhoto) {
		this.askSubject = askSubject;
		this.askContent = askContent;
		this.askPhoto = askPhoto;
	}
}
