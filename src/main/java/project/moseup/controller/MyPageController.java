package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Team;
import project.moseup.dto.MyInfoDto;
import project.moseup.service.TeamService;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.MyPageService;
import project.moseup.validator.CheckRealize;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {

    private final MemberService memberService;
    private final MyPageService myPageService;
    private final CheckRealize checkRealize;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myPage")
    public String myPage(Model model, Principal principal) {
        System.out.println("principal getname : " + principal.getName());
        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("member", member);
        return "myPage/myPage";
    }

    @GetMapping("/myTeamList")
    public String myTeamList(Model model, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        List<Team> teams = this.myPageService.findByMember(member);

        model.addAttribute("teamList", teams);
        model.addAttribute("member", member);
        return "myPage/myTeamList";
    }
//
//    @GetMapping("/myInfoMain")
//    public String myInfoMain(Model model, Principal principal){
//        Member member = this.memberService.getMember(principal.getName());
//        model.addAttribute("member", member);
//        return "myPage/myInfoMain";
//    }
//
//    /** 비밀번호 체크 **/
//    @GetMapping(value = "/pwChk", produces = "text/html;charset=utf-8")
//    @ResponseBody
//    public String passwordCheck(String scan, Principal principal){
//        Member member = this.memberService.getMember(principal.getName());
//        String origin = member.getPassword();
//        String msg = checkRealize.passwordCheck(origin, scan);
//        return msg;
//    }

    @GetMapping("/myInfo")
    public String myInfo(MyInfoDto myInfoDto, Model model, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("member",member);

        myInfoDto = myInfoDto.toDto(member);
        model.addAttribute("myInfoDto", myInfoDto);

        MemberGender[] genders = MemberGender.values();
        model.addAttribute("genders", genders);
        return "myPage/myInfo";
    }

//    @PostMapping("/myInfo")
//    public String myInfoUpdate(@RequestParam Long mno){
//
//        return "redirect:/myPage/myInfo?mno="+mno;
//    }
}
