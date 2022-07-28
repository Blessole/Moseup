package project.moseup.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_no")
	private Long mno;
	
	@NotEmpty
	@Column(name = "member_email", unique = true)
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

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public Member(String email, String password, String nickname, String name, MemberGender gender, String address, String phone, String photo, DeleteStatus memberDelete, LocalDateTime memberDate, Role role) {
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
		this.role = role;
	}

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
