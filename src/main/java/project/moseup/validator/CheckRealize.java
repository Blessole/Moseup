package project.moseup.validator;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.moseup.domain.Member;
import project.moseup.repository.member.MemberRepository;
import project.moseup.service.member.MemberService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CheckRealize extends Check<String> {

    private final MemberRepository memberRepository;

    /** 이메일 중복 검증 **/
    @Override
    public String emailCheck(String value){
        String msg="";
        List<Member> findMembers = memberRepository.findByEmail(value);
        if (findMembers.isEmpty()){
            System.out.println("CheckRealize validateDuplicate 지나감");
            msg = "사용 가능한 이메일입니다.";
        }
        else msg="이미 사용중인 이메일입니다.";
        return msg;
    }

    /** 닉네임 중복 검증 **/
    @Override
    public String nicknameCheck(String value){
        String msg="";
        String regExp = "^[ㄱ-ㅎ가-힣A-Za-z0-9-_]{2,10}$";
        List<Member> findMembers = memberRepository.findByNickname(value);
        if (findMembers.isEmpty()){
            System.out.println("Service validateDuplicate 지나감");
            msg = "사용 가능한 닉네임입니다.";
            if(!value.matches(regExp)) {
                msg = "닉네임은 특수문자를 제외한 2~10자리여야 합니당!";
            }
        } else msg="이미 사용중인 닉네임입니다.";
        return msg;
    }

    /** 비밀번호 중복 검증 **/
    @Override
    public String passwordCheck(String password1, String password2){
        String msg = "";
        if(!password1.equals(password2)){
            msg = "비밀번호가 일치하지 않습니다.";
        } else  msg = "비밀번호가 일치합니다.";
        return msg;
    }
}
