package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.service.TeamService;
import project.moseup.service.member.MemberService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {

    private final MemberService memberService;
    private final TeamService teamService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myPage")
    public String myPage(Model model, Principal principal) {
        System.out.println("principal getname : " + principal.getName());
        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("member", member);
        return "myPage/myPage";
    }

    @GetMapping("/myTeamList")
    public String myTeamList(Model model){
//        Team team = this.teamService
        return "myPage/myStudyList";
    }
}
