package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.moseup.domain.AskBoard;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardSaveReqDto;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.AskBoardService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        List<AskBoard> askboardList = askBoardService.findAskBoards(member);

        model.addAttribute("member", member);
        model.addAttribute("askboardList", askboardList);
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
}
