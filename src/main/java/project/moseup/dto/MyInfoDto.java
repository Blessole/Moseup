package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
public class MyInfoDto {

    @NotEmpty(message = "이메일 주소를 입력해주세요")
    @Email(message = "올바른 이메일 주소를 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 8, message = "비밀번호는 4자리 이상, 8자리 이하로 입력해주세요")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수입니다.")
    private String password2;

    @NotEmpty(message = "닉네임을 입력해주세요")
    @Size(min=2, max=10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nickname;

    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    private MemberGender gender;

    @NotEmpty(message = "주소를 입력해주세요")
    private String address;

    @NotEmpty(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 전화번호를 입력해주세요")
    private String phone;

    private String photo;
    private DeleteStatus memberDelete;
    private LocalDateTime memberDate;
    private Role role;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password1)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .address(address)
                .phone(phone)
                .photo(photo)
                .memberDelete(DeleteStatus.FALSE)
                .memberDate(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }

    public MyInfoDto toDto(Member memberPS){
        this.name = memberPS.getName();
        this.nickname = memberPS.getNickname();
        this.email = memberPS.getEmail();
        this.gender = memberPS.getGender();
        this.phone = memberPS.getPhone();
        this.photo = memberPS.getPhoto();
        this.address = memberPS.getAddress();
        this.password1 = memberPS.getPassword();
        this.password2 = memberPS.getPassword();
        this.memberDelete = memberPS.getMemberDelete();
        this.memberDate = memberPS.getMemberDate();
        this.role = role;

        return this;
    }
}
