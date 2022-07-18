package project.moseup.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

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
	
	@NotEmpty
	@Column(name = "ask_date")
	private LocalDateTime askDate;
	
	@NotEmpty
	@Enumerated(EnumType.STRING)
	private DeleteStatus askDelete;
	
	@OneToMany(mappedBy = "askBoard")
	private List<AskBoardReply> askBoardReplies = new ArrayList<>();
	
}
