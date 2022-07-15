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
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_no")
	private int mno;
	
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
	private DeleteStatus delete;
	
	@NotEmpty
	@Column(name = "member_date")
	private LocalDateTime date;
	
	@OneToMany(mappedBy = "member")
    private List<Team> teams = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();
	
	@OneToOne(mappedBy = "member")
	private List<BankBook> bankbooks = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<FreeBoard> freeboard = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<AskBoard> askboard = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<AskBoardReply> askboardReply = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<TeamAskBoard> teamAskboard = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<TeamAskBoardReply> teamAskboardReply = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<CertificationBoard> certificationboard = new ArrayList<>();

}
