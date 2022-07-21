package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import project.moseup.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @GetMapping("/members/join")
    public String joinForm(Model model){

        return "members/joinForm";
    }

    @GetMapping("/members/join")
    public String join(BindingResult result){
        if(result.hasErrors()){
            return "members/joinForm";
        }

        //String addr1 = model.getAttribute("postAddr1", postAddr1);

        return "redirect:/";		//첫 화면으로 돌아감
    }
}
