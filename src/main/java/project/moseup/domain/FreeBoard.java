package project.moseup.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class FreeBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "free_no")
	private Long fno;				//게시물 번호

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;				//회원 번호

	@NotEmpty
	@Column(name = "free_title")
	private String freeTitle;			//게시물 제목

	@NotEmpty
	@Column(name = "free_content")
	private String freeContent;		//게시물 내용

	@Column(name = "free_like")
	private int freeLike;				//좋아요

	@Column(name = "free_date")
	private LocalDateTime freeDate;	//게시물 작성일

	@Column(name = "free_readcount")
	private int freeReadCount;				//조회수

	@Enumerated(EnumType.STRING)
	private DeleteStatus freeDelete;	//게시물 삭제 여부

	@OneToMany(mappedBy = "freeBoard")
	private List<FreeBoardReply> freeBoardReplies = new ArrayList<>();
}
