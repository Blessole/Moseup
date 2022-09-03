package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.service.admin.AdminTeamService;
import project.moseup.service.member.MemberService;

import java.security.Principal;

@Controller
@Log4j2
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminTeamController {

    private final AdminTeamService adminTeamService;
    private final MemberService memberService;

    // 공용 데이터
    @ModelAttribute
    public void loginMember(Principal principal, Model model){
            Member member = memberService.getPrincipal(principal);
            model.addAttribute("loginMember", member);
    }

    @GetMapping("/teamList")
    public String teamList(@RequestParam(required = false, defaultValue = "")String keyword, Model model,
                           @PageableDefault(size = 15, sort = "tno", direction = Sort.Direction.DESC) Pageable pageable){
        log.info("teamList - 지나감");

        Page<Team> teams = adminTeamService.teams(keyword, pageable);

        int startPage = Math.max(1, teams.getPageable().getPageNumber() - 5);
        int endPage = Math.min(teams.getTotalPages(), teams.getPageable().getPageNumber() + 5);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("teams", teams);

        return "admin/teamList";
    }

    // 회원 정보 상세보기
    @GetMapping("/teamDetail")
    public String memberDetail(@RequestParam Long tno, @RequestParam(required = false, defaultValue = "0") int pageNum, Model model){
            Team team = adminTeamService.teamDetail(tno);
        log.info("teamDetail - 지나감");

            model.addAttribute("team", team);
            model.addAttribute("pageNum", pageNum);

            return "admin/teamDetail";
        }

}
