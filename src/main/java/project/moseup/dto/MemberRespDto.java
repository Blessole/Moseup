package project.moseup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberRespDto {

    private Long mno;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private MemberGender gender;
    private String address;
    private String phone;
    private String photo;
    private DeleteStatus memberDelete;
    private LocalDate memberDate;
    private Role role;

    public MemberRespDto toDto(Member memberPS){
        this.mno = memberPS.getMno();
        this.email = memberPS.getEmail();
        this.password = memberPS.getPassword();
        this.nickname = memberPS.getNickname();
        this.name = memberPS.getName();
        this.gender = memberPS.getGender();
        this.address = memberPS.getAddress();
        this.phone = memberPS.getPhone();
        this.photo = memberPS.getPhoto();
        this.memberDelete = memberPS.getMemberDelete();
        this.memberDate = memberPS.getMemberDate();
        this.role = memberPS.getRole();
        return this;
    }

}
