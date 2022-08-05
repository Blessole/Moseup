package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.JoinForm;
import project.moseup.service.member.MemberService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @GetMapping("/joinForm")
    public String joinForm(Model model){
        //해당 enum의 모든 정보를 배열로 반환 [MALE, FEMALE]
        MemberGender[] genders = MemberGender.values();
        model.addAttribute("genders", genders);
        model.addAttribute("joinForm", new JoinForm());
        return "members/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute(value = "joinForm") JoinForm joinForm, BindingResult bindingResult){
        System.out.println("error:"+ bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            return "members/joinForm";
        }

        if (!joinForm.getPassword1().equals(joinForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "members/joinForm";
        }

        try {
            joinForm.toEntity();
            memberService.join(joinForm);
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
            return "members/joinForm";
        } catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "members/joinForm";
        }

        //String addr1 = model.getAttribute(address);
        //model.getAttribute("postAddr1", postAddr1);
        //String addr1 =

        return "redirect:/";
    }

//     중복체크
    @PostMapping(value = "/emailCheck", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String emailChk(String email){
        String msg="";
        List<Member> member = memberService.validateDuplicateMember(email);
        if(member == null) msg = "사용 가능한 이메일입니다.";
        else msg="이미 사용중인 이메일입니다.";
        return msg;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "members/loginForm";
    }


}
