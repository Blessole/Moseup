package project.moseup.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_no")
	private Long mno;

	public Member(){}
	public Member(String email, String password, String nickname, String name) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
	}

	@NotEmpty
	@Column(name = "member_email")
	@Email(message = "올바른 이메일 주소를 입력해주세요")
	private String email;
	
	@NotEmpty
	@Column(name = "member_password")
	@Size(min = 10, max = 30, message = "비밀번호는 10자 이상 30자 이하로 입력해주세요")
	private String password;
	
	@NotEmpty
	@Column(name = "member_nickname")
	private String nickname;
	
	@NotEmpty
	@Column(name = "member_name")
	private String name;
	


	@OneToMany(mappedBy = "teamMember")
    private List<Team> teams = new ArrayList<>();

	@OneToMany(mappedBy = "likeMember")
    private List<Likes> likes = new ArrayList<>();

	@OneToMany(mappedBy = "teamInMember")
    private List<TeamMember> teamMembers = new ArrayList<>();

	@OneToOne(mappedBy = "bankMember")
	private BankBook bankbooks = new BankBook();

	@OneToMany(mappedBy = "freeBoardMember")
	private List<FreeBoard> freeBoards = new ArrayList<>();

	@OneToMany(mappedBy = "askBoardMember")
	private List<AskBoard> askBoards = new ArrayList<>();

	@OneToMany(mappedBy = "askBoardReplyMember")
	private List<AskBoardReply> askBoardReply = new ArrayList<>();

	@OneToMany(mappedBy = "teamAskBoardMember")
	private List<TeamAskBoard> teamAskBoards = new ArrayList<>();

	@OneToMany(mappedBy = "teamAskBoardReplyMember")
	private List<TeamAskBoardReply> teamAskBoardReply = new ArrayList<>();

	@OneToMany(mappedBy = "certificationBoardMember")
	private List<CertificationBoard> certificationBoards = new ArrayList<>();

}
