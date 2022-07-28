package project.moseup.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

	@OneToMany(mappedBy = "askBoard")
	private List<AskBoardReply> askBoardReplies = new ArrayList<>();

}
