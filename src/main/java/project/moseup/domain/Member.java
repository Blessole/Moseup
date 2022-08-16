package project.moseup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import project.moseup.dto.MemberSaveReqDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//생성자를 따로 안 만들면 자동으로 기본 생성자가 생성됨 하지만 다른 생성자가 있으면 기본 생성자를 만들어 줘야 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "members")
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

	private String loginType;

	@Builder //빌더 어노테이션을 명시하면 생성자에 독립적으로 사용 가능함 원하는 값만 넣을 수 있고 순서가 중요하지 않음 setter X
	public Member(String email, String password, String nickname, String name, MemberGender gender, String address, String phone, String photo, DeleteStatus memberDelete, LocalDateTime memberDate, Role role, String loginType) {
		// 안전한 객체 생성 패턴 = 필요한 값이 없는 경우에 NULL 예외가 발생해 메시지를 보여주고 흐름 종료
		Assert.hasText(email, "이메일은 [NULL]이 될 수 없습니다");
		Assert.hasText(password, "비밀번호는 [NULL]이 될 수 없습니다");
		Assert.hasText(nickname, "닉네임은 [NULL]이 될 수 없습니다");
		Assert.hasText(name, "이름은 [NULL]이 될 수 없습니다");
		Assert.hasText(address, "주소는 [NULL]이 될 수 없습니다");
		Assert.hasText(phone, "전화번호는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(memberDelete), "탈퇴여부는 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(memberDate), "회원생성일은 [NULL]이 될 수 없습니다");
		Assert.hasText(String.valueOf(role), "권한은 [NULL]이 될 수 없습니다");

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
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "Member {" +
				"\n mno = " + mno +
				"\n email = '" + email + '\'' +
				"\n password = '" + password + '\'' +
				"\n nickname = '" + nickname + '\'' +
				"\n name = '" + name + '\'' +
				"\n gender = " + gender +
				"\n address = '" + address + '\'' +
				"\n phone = '" + phone + '\'' +
				"\n photo = '" + photo + '\'' +
				"\n memberDelete = " + memberDelete +
				"\n memberDate = " + memberDate +
				"\n role = " + role +
				"\n }";
	}

	// 엔티티 데이터를 수정해야 한다면 update 사용
	public Member deleteUpdate(DeleteStatus deleteStatus){
		this.memberDelete = deleteStatus;
		return this;
	}

	public Member newMember() {
		return new Member();
	}

	// 정보 수정
	public void infoUpdate(MemberSaveReqDto memberDto){
		this.name = memberDto.getName();
		this.nickname = memberDto.getNickname();
		this.gender = memberDto.getGender();
		this.photo = memberDto.getPhoto();
		this.phone = memberDto.getPhone();
		this.address = memberDto.getAddress();
	}

	// 비밀번호 암호화
	public void encodePassword(PasswordEncoder passwordEncoder){
		this.password = passwordEncoder.encode(password);
	}

	// 연관관계 맵핑
	@JsonIgnore
	@OneToMany(mappedBy = "member")
    private List<Team> teams = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member")
    private List<Likes> likes = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();

	@JsonIgnore
	@OneToOne(mappedBy = "member")
	private Bankbook bankbook;

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<FreeBoard> freeBoards = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<FreeBoardReply> freeBoardReplies = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<AskBoard> askBoards = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<AskBoardReply> askBoardReplies = new ArrayList<>();


	@OneToMany(mappedBy = "member")
	private List<TeamAskBoard> teamAskBoards = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<TeamAskBoardReply> teamAskBoardReplies = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<CheckBoard> checkBoards = new ArrayList<>();

}
