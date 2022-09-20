package project.moseup.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.moseup.dto.MemberRespDto;

//생성자를 따로 안 만들면 자동으로 기본 생성자가 생성됨 하지만 다른 생성자가 있으면 기본 생성자를 만들어 줘야 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Slf4j
@Table(name = "members")
public class Member implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private LocalDate memberDate;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String loginType;

	@Builder //빌더 어노테이션을 명시하면 생성자에 독립적으로 사용 가능함 원하는 값만 넣을 수 있고 순서가 중요하지 않음 setter X
	public Member(String email, String password, String nickname, String name, MemberGender gender, String address, String phone, String photo, DeleteStatus memberDelete, LocalDate memberDate, Role role, String loginType) {
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

	// 아이디, 비밀번호 찾기용 builder
	@Builder(builderClassName = "findIdBuilder", builderMethodName = "findIdBuilder")
	public Member(String first, String second) {

		if (first.contains("@")){
			System.out.println("비밀번호 찾기 로직 지나감");
			Assert.hasText(first, "이메일은 [NULL]이 될 수 없습니다");
			Assert.hasText(second, "이름은 [NULL]이 될 수 없습니다");
			this.email = first;
			this.name = second;
		} else {
			System.out.println("아이디 찾기 로직 지나감");
			Assert.hasText(first, "이름은 [NULL]이 될 수 없습니다");
			Assert.hasText(second, "전화번호는 [NULL]이 될 수 없습니다");

			this.name = first;
			this.phone = second;
		}
	}

	// 회원정보 수정용 builder
	@Builder(builderClassName = "myInfoBuilder", builderMethodName = "myInfoBuilder")
	public Member(String password, String nickname, String name, MemberGender gender, String address, String phone, String photo) {
		Assert.hasText(password, "비밀번호는 [NULL]이 될 수 없습니다");
		Assert.hasText(nickname, "닉네임은 [NULL]이 될 수 없습니다");
		Assert.hasText(name, "이름은 [NULL]이 될 수 없습니다");
		Assert.hasText(address, "주소는 [NULL]이 될 수 없습니다");
		Assert.hasText(phone, "전화번호는 [NULL]이 될 수 없습니다");

		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.photo = photo;
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
	public void deleteUpdate(DeleteStatus deleteStatus){
		this.memberDelete = deleteStatus;
	}

	public Member newMember() {
		return new Member();
	}

	// 정보 수정
	public void infoUpdate(Member member){
		this.name = member.getName();
		this.nickname = member.getNickname();
		this.gender = member.getGender();
		this.photo = member.getPhoto();
		this.phone = member.getPhone();
		this.address = member.getAddress();
		this.password = member.getPassword();
	}

	// 비밀번호 암호화
	public void encodePassword(PasswordEncoder passwordEncoder){
		this.password = passwordEncoder.encode(password);
	}
	public void updatePassword(String encryptPassword) {
		this.password = encryptPassword;
	}

	public String getPhotoViewPath(){
		int index = this.photo.indexOf("images");
		return this.photo.substring(index - 1);
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
	@OneToMany(mappedBy = "member")
	private List<Bankbook> bankbooks = new ArrayList<>();

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

	@JsonIgnore
	@OneToMany(mappedBy = "member")
	private List<TeamBankbookDetail> teamBankbookDetails = new ArrayList<>();

	public MemberRespDto toDto() {
		return new MemberRespDto().toDto(this);
	}


	/** Spring security 구현용 시작 **/

	/** 해당 유저의 권한 목록 **/
	// 사용자의 권한을 콜렉션 형태로 반환
	// 단, 클래스 자료형은 GrantedAuthority를 구현해야 함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority(role.getValue()));
		log.info("role.getValue() : " + role.getValue());
		return roles;
	}

	/** 사용자의 unique 한 값 **/
	@Override
	public String getUsername() {
		return email;
	}

	/** 계정 만료 여부 **/
	// true : 만료 X, false : 만료
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/** 계정 잠김 여부 **/
	// true : 잠기지 X, false : 잠김
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/** 비밀번호 만료 여부 **/
	// true : 만료 X, false : 만료
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/** 사용자 활성 여부 **/
	// true : 활성 O, false : 비활성화
	@Override
	public boolean isEnabled() {
		boolean locked = false;
		if (memberDelete == DeleteStatus.FALSE){
			locked = true;
		} else if (memberDelete == DeleteStatus.TRUE){
			locked = false;
		}
		return locked;
	}
}
