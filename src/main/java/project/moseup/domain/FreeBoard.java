package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class FreeBoard {

	@Id @GeneratedValue
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

	public FreeBoard(){};

	@Builder
	public FreeBoard(Member member, String freeTitle, String freeContent, int freeLike, LocalDateTime freeDate, int freeReadCount, DeleteStatus freeDelete) {
		this.member = member;
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
		this.freeLike = freeLike;
		this.freeDate = freeDate;
		this.freeReadCount = freeReadCount;
		this.freeDelete = freeDelete;
	}
	
}
