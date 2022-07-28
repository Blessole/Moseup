package project.moseup.dto;

import lombok.Getter;
import lombok.Setter;
import project.moseup.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class LoginForm {
    @NotBlank(message = "이메일 주소를 입력해주세요")
    @Email(message = "올바른 이메일 주소를 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    public Member toLogin(){
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
