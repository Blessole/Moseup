package project.moseup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;

@Getter @Setter
public class KakaoLoginForm {

    private String email;
    private String name;
    private String photo;
    private String gender;

    @Builder
    public KakaoLoginForm(String email, String name, String img, String gender){
        Assert.hasText(email, "이메일은 [NULL]이 될 수 없습니다");
        Assert.hasText(name, "비밀번호는 [NULL]이 될 수 없습니다");
        Assert.hasText(img, "닉네임은 [NULL]이 될 수 없습니다");
        Assert.hasText(gender, "이름은 [NULL]이 될 수 없습니다");

        this.email = email;
        this.name = name.replaceAll(" ", "");
        this.gender = gender;
        this.photo = img;
    }

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .name(name)
                .photo(photo)
                .loginType("KAKAO_LOGIN_TYPE")
                .build();
    }
}
