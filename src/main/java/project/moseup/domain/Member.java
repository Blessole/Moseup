package project.moseup.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor //기본 생성자 생성자를 따로 안 만들면 자동으로 생성됨 하지만 다른 생성자가 있으면 기본 생성자를 만들어 줘야 함
@Entity
@Getter @Setter
@Table(name = "members")
public class Member {

	@Builder //빌더 어노테이션을 명시하면 생성자에 독립적으로 사용 가능함 원하는 값만 넣을 수 있고 순서가 중요하지 않음
	public Member(Long mno, String email, String password, String nickname, String name, MemberGender gender, String address, String phone, String photo, DeleteStatus memberDelete, LocalDateTime memberDate) {
		this.mno = mno;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.photo = photo;
		this.memberDelete = memberDelete;
		this.memberDate = memberDate;
	}

	@Id @GeneratedValue
	@Column(name = "member_no")
	private Long mno;
	
	@NotEmpty
	@Column(name = "member_email")
	private String email;
	
	@NotEmpty
	@Column(name = "member_password")
	private String password;
	
	@NotEmpty
	@Column(name = "member_nickname")
	private String nickname;
	
	@NotEmpty
	@Column(name = "member_name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private MemberGender gender;
	
	@NotEmpty
	@Column(name = "member_address")
	private String address;
	
	@NotEmpty
	@Column(name = "member_phone")
	private String phone;
	
	@Column(name = "member_photo")
	private String photo;
	
	@Enumerated(EnumType.STRING)
	private DeleteStatus memberDelete;

	@Column(name = "member_date")
	private LocalDateTime memberDate;

	// 연관관계 맵핑
	@OneToMany(mappedBy = "member")
    private List<Team> teams = new ArrayList<>();

	@OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();
	
	@OneToOne(mappedBy = "member")
	private Bankbook bankbook;

	@OneToMany(mappedBy = "member")
	private List<FreeBoard> freeBoards = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<FreeBoardReply> freeBoardReplies = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<AskBoard> askBoards = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<AskBoardReply> askBoardReplies = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<TeamAskBoard> teamAskBoards = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<TeamAskBoardReply> teamAskBoardReplies = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<CheckBoard> checkBoards = new ArrayList<>();

}
