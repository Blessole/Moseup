package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.JoinForm;
import project.moseup.dto.LoginForm;
import project.moseup.service.member.MemberService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @GetMapping("/members/joinForm")
    public String joinForm(Model model){
        System.out.println("joinForm 지나간당");
        //해당 enum의 모든 정보를 배열로 반환 [MALE, FEMALE]
        MemberGender[] genders = MemberGender.values();
        model.addAttribute("genders", genders);
        model.addAttribute("joinForm", new JoinForm());
        return "members/joinForm";
    }

    @PostMapping("/members/join")
    public String join(@Valid @ModelAttribute(value = "joinForm") JoinForm joinForm, BindingResult bindingResult, Model model, Member member){
        System.out.println("error:"+ bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            return "members/joinForm";
        }

        joinForm.toEntity();

        //String addr1 = model.getAttribute(address);
        //model.getAttribute("postAddr1", postAddr1);
        //String addr1 =
        memberService.join(joinForm);

        return "redirect:/members/loginForm";		//첫 화면으로 돌아감
    }

    @GetMapping("/members/loginForm")
    public String loginForm(Model model){
        System.out.println("loginForm지나가는중~");
        model.addAttribute("loginForm", new LoginForm());
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model){
        System.out.println("loginAction지나가는중");

        Member member = loginForm.toLogin();
        System.out.println("member email : " + member.getEmail());
        if(memberService.login(member)){
            System.out.println("로그인성공");
            return "members/loginSuccess";
        }
        System.out.println("로그인 실패");

        return "members/loginForm";
    }
}
