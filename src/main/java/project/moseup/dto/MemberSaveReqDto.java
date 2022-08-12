package project.moseup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter //컨트롤러 에서 setter 가 호출되면서 dto 에 값이 채워짐
public class MemberSaveReqDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;

//  패스워드 검증용
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password2;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.") // null, "", " "(빈공백문자열) 허용x
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

    private String address2;

    @NotBlank
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phone;

    private String photo;

    private MemberGender gender;

    private Role role;

    private DeleteStatus memberDelete;

    private LocalDateTime memberDate;

    public MemberSaveReqDto() {}

    @Builder
    public MemberSaveReqDto(String email, String password, String name, String nickname, MemberGender gender, String address, String phone, String photo) {
        // 안전한 객체 생성 패턴 = 필요한 값이 없는 경우에 NULL 예외가 발생해 메시지를 보여주고 흐름 종료
        Assert.hasText(email, "이메일은 [NULL]이 될 수 없습니다");
        Assert.hasText(password, "비밀번호는 [NULL]이 될 수 없습니다");
        Assert.hasText(nickname, "닉네임은 [NULL]이 될 수 없습니다");
        Assert.hasText(name, "이름은 [NULL]이 될 수 없습니다");
        Assert.hasText(address, "주소는 [NULL]이 될 수 없습니다");
        Assert.hasText(phone, "전화번호는 [NULL]이 될 수 없습니다");

        this.email = email;
        this.password = password;
        this.name = name.replaceAll(" ", "");
        this.nickname = nickname;
        this.gender = gender;
        this.address = address + " " + address2;
        this.phone = phone;
        this.photo = photo;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .name(name.replaceAll(" ", ""))
                .nickname(nickname)
                .address(address + " " + address2)
                .phone(phone)
                .photo(photo)
                .memberDelete(DeleteStatus.FALSE)
                .gender(gender)
                .memberDate(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }

    public MemberSaveReqDto toDto(Member memberPS){
        this.name = memberPS.getName();
        this.nickname = memberPS.getNickname();
        this.email = memberPS.getEmail();
        this.gender = memberPS.getGender();
        this.phone = memberPS.getPhone();
        this.photo = memberPS.getPhoto();
        this.address = memberPS.getAddress();
        this.password = memberPS.getPassword();
        this.password2 = memberPS.getPassword();
        this.memberDelete = memberPS.getMemberDelete();
        this.memberDate = memberPS.getMemberDate();
        this.role = memberPS.getRole();

        return this;
    }
}
