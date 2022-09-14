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
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.teamPage.TeamDetailDto;
import project.moseup.exception.NoLoginException;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.admin.AdminTeamService;
import project.moseup.service.member.MemberService;

import java.security.Principal;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminTeamController {

    private final AdminTeamService adminTeamService;
    private final MemberService memberService;
    private final AdminMemberService adminMemberService;

    // 공용 데이터
    @ModelAttribute
    public void loginMember(Principal principal, Model model){
        if(principal == null){
            throw new NoLoginException();
        }else{
            Member member = memberService.getPrincipal(principal);
            Map<String, Object> memberMap = adminMemberService.getMemberMap(member.getMno());

            model.addAttribute("memberMap", memberMap);
        }
    }

    @GetMapping("/teamList")
    public String teamList(@RequestParam(required = false, defaultValue = "")String keyword, Model model,
                           @PageableDefault(size = 15, sort = "tno", direction = Sort.Direction.DESC) Pageable pageable){

        Page<Team> teams = adminTeamService.teams(keyword, pageable);

        int startPage = Math.max(1, teams.getPageable().getPageNumber() - 5);
        int endPage = Math.min(teams.getTotalPages(), teams.getPageable().getPageNumber() + 5);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("teams", teams);

        return "admin/teamList";
    }

    // 팀 정보 상세보기
    @GetMapping("/teamDetail")
    public String memberDetail(@RequestParam Long tno, @RequestParam(required = false, defaultValue = "0") int pageNum, Model model){
            TeamDetailDto team = adminTeamService.teamDetail(tno);

            model.addAttribute("team", team);
            model.addAttribute("deleteFalse", DeleteStatus.FALSE);
            model.addAttribute("pageNum", pageNum);

            return "admin/teamDetail";
    }
    
    // 팀 통장 정보
    @GetMapping("/teamBankbook")
    public String teamBankbook(@RequestParam Long tno, @RequestParam int pageNum, Model model){
        TeamDetailDto team = adminTeamService.teamDetail(tno);
        Map<String, Object> teamAndDetailsDESC = adminTeamService.getTeamBankbook(tno);

        model.addAttribute("team", team);
        model.addAttribute("teamMap", teamAndDetailsDESC);
        model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        model.addAttribute("pageNum", pageNum);

        return "admin/teamBankbook";
    }

    // 팀 인증글
    @GetMapping("/teamCheckBoard")
    public String teamCheckBoard(@RequestParam Long tno, @RequestParam int pageNum, Model model){
        TeamDetailDto team = adminTeamService.teamDetail(tno);
        Map<String, Object> checkBoardsDesc = adminTeamService.getCheckBoard(tno);

        model.addAttribute("team", team);
        model.addAttribute("checkBoardMap", checkBoardsDesc);
        model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        model.addAttribute("pageNum", pageNum);

        return "admin/teamCheckBoard";
    }

    // 팀 문의글
    @GetMapping("/teamAskBoard")
    public String teamAskBoard(@RequestParam Long tno, @RequestParam int pageNum, Model model){
        TeamDetailDto team = adminTeamService.teamDetail(tno);

        model.addAttribute("team", team);
        model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        model.addAttribute("pageNum", pageNum);

        return "admin/teamAskBoard";
    }

    // 팀 가입 멤버
    @GetMapping("/teamInMember")
    public String teamInMember(@RequestParam Long tno, @RequestParam int pageNum, Model model){
        TeamDetailDto team = adminTeamService.teamDetail(tno);

        model.addAttribute("team", team);
        model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        model.addAttribute("pageNum", pageNum);

        return "admin/teamInMember";
    }


}
