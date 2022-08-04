package project.moseup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Role;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter @Setter
public class JoinForm {

    @NotBlank(message = "이메일 주소를 입력해주세요")
    @Email(message = "올바른 이메일 주소를 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 4, max = 8, message = "비밀번호는 4자리 이상, 8자리 이하로 입력해주세요")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min=2, max=10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    private MemberGender gender;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 전화번호를 입력해주세요")
    private String phone;

    private String photo;
    private DeleteStatus memberDelete;
    private LocalDateTime memberDate;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .address(address)
                .phone(phone)
                .photo(photo)
                .memberDelete(DeleteStatus.FALSE)
                .memberDate(memberDate)
                .role(Role.USER)
                .build();
    }

//    @Builder
//    public JoinForm(String email, String password, String nickname, String name, MemberGender gender, String address, String phone, String photo, DeleteStatus memberDelete, LocalDateTime memberDate) {
//        this.email = email;
//        this.password = password;
//        this.nickname = nickname;
//        this.name = name;
//        this.gender = gender;
//        this.address = address;
//        this.phone = phone;
//        this.photo = photo;
//        this.memberDelete = memberDelete;
//        this.memberDate = memberDate;
//    }
}
