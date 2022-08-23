package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import project.moseup.domain.AskBoard;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardSaveReqDto;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.AskBoardService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("askBoard")
public class AskBoardController {

    private final MemberService memberService;
    private final AskBoardService askBoardService;

    @GetMapping("/askBoardList")
    public String askBoardList(Model model, Principal principal){
        Member member = this.memberService.getMember(principal.getName());

        List<AskBoard> askBoardList = askBoardService.findAskBoards(member);

        model.addAttribute("member", member);
        model.addAttribute("askBoardList", askBoardList);
        return "myPage/askBoardList";
    }

    @GetMapping("/askBoardForm")
    public String askBoardForm(Model model, Principal principal){
        Member member = this.memberService.getMember(principal.getName());

        model.addAttribute("askBoardForm", new AskBoardSaveReqDto());
        model.addAttribute("member", member);
        return "myPage/askBoardForm";
    }

    @PostMapping("/ask")
    public String askBoardAction(@ModelAttribute("askBoardForm") AskBoardSaveReqDto askBoardForm, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        askBoardForm.setMember(member);

        askBoardService.save(askBoardForm);

        return "redirect:/askBoard/askBoardList";
    }

    @GetMapping("/askBoardDetail")
    public String askBoardDetail(@RequestParam  Long ano, Model model, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        AskBoard askBoard = this.askBoardService.findOne(ano);
        model.addAttribute("askBoard", askBoard);
        model.addAttribute("member", member);

        // clean code 생각해보기 - detail & update 코드 동일!
        return "/myPage/askBoardDetail";
    }

    /** 글 수정 **/
    @GetMapping("/askBoardUpdateForm")
    public String askBoardUpdateForm(@RequestParam("ano") Long ano, Model model, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        AskBoardSaveReqDto askBoardDto = this.askBoardService.getPost(ano);
        model.addAttribute("ano", ano);
        model.addAttribute("askBoardDto", askBoardDto);
        model.addAttribute("member", member);
        return "/myPage/askBoardUpdateForm";
    }

    @PostMapping("/askUpdate")
    public String askBoardUpdate(@ModelAttribute("askBoardDto") AskBoardSaveReqDto askBoardDto, BindingResult bindingResult,
                                 @RequestParam("ano") Long ano, Model model, Principal principal){
        System.out.println("error:"+ bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            return "redirect:/askBoard/askUpdate?ano="+ano;
        }

        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("member", member);
        askBoardService.update(askBoardDto, ano);

        return "redirect:/askBoard/askBoardDetail?ano="+ano;
    }

    /** 글 삭제 **/
    @GetMapping("/askBoardDelete")
    public String askBoardDelete(@RequestParam Long ano, Model model, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("member", member);

        askBoardService.deleteBoard(ano);
        return "redirect:/askBoard/askBoardList";
    }
}
