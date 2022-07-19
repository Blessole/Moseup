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
public class FreeBoard {

	@Id @GeneratedValue
	@Column(name = "free_no")
	private Long fno;				//게시물 번호

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no")
	private Member freeBoardMember;			//회원번호

	@NotEmpty
	@Column(name = "free_title")
	private String ftitle;			//게시물 제목

	@NotEmpty
	@Column(name = "free_content")
	private String fcontent;		//게시물 내용

	@Column(name = "free_like")
	private int flike;				//좋아요

	@NotEmpty
	@Column(name = "free_date")
	private LocalDateTime fdate;	//게시물 작성일

	@Column(name = "free_readcount")
	private int fcount;				//조회수

	@Enumerated(EnumType.STRING)
	private DeleteStatus freeBoardDelete;	//게시물 삭제 여부

	@OneToMany(mappedBy = "freeboard")
	private List<FreeBoardReply> freeBoardReply = new ArrayList<>();
}
