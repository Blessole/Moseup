package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.JoinForm;
import project.moseup.service.member.MemberService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @GetMapping("/joinForm")
    public String joinForm(Model model){
        System.out.println("joinForm 지나간당");
        //해당 enum의 모든 정보를 배열로 반환 [MALE, FEMALE]
        MemberGender[] genders = MemberGender.values();
        model.addAttribute("genders", genders);
        model.addAttribute("joinForm", new JoinForm());
        return "members/joinForm";
    }

    @PostMapping("/join")
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

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        System.out.println("loginForm지나가는중~");
        return "members/loginForm";
    }

    @GetMapping("/myPage")
    public String myPage(Model model) {

        return "members/myPage";
    }
}
