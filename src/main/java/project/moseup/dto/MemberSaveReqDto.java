package project.moseup.dto;

import lombok.Setter;
import project.moseup.domain.Member;

@Setter //컨트롤러에서 setter 가 호출되면서 dto 에 값이 채워짐
public class MemberSaveReqDto {

    private String password;
    private String nickname;
    private String address;
    private String phone;
    private String photo;

    public Member toEntity(){
        return Member.builder()
                .password(password)
                .nickname(nickname)
                .address(address)
                .phone(phone)
                .photo(photo)
                .build();
    }
}
