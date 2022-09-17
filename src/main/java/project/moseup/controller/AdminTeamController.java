package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.TeamAskBoardReplySaveReqDto;
import project.moseup.dto.TeamAskReplyDeleteAndRecoverDto;
import project.moseup.dto.searchDto.TeamSearchDto;
import project.moseup.dto.teamPage.TeamDetailRespDto;
import project.moseup.exception.NoLoginException;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.admin.AdminTeamService;
import project.moseup.service.member.MemberService;
import project.moseup.service.teampage.TeamAskBoardReplyService;

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

    private final TeamAskBoardReplyService teamAskBoardReplyService;

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
    public String teamList(@ModelAttribute TeamSearchDto searchDto,
                           @PageableDefault(size = 15, sort = "tno", direction = Sort.Direction.DESC) Pageable pageable,
                           Model model){

        Page<Team> teams = adminTeamService.teams(searchDto, pageable);

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
            TeamDetailRespDto team = adminTeamService.teamDetail(tno);

            model.addAttribute("team", team);
            model.addAttribute("deleteFalse", DeleteStatus.FALSE);
            model.addAttribute("pageNum", pageNum);

            return "admin/teamDetail";
    }
    
    // 팀 통장 정보
    @GetMapping("/teamBankbook")
    public String teamBankbook(@RequestParam Long tno, @RequestParam int pageNum, Model model){
        TeamDetailRespDto team = adminTeamService.teamDetail(tno);
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
        TeamDetailRespDto team = adminTeamService.teamDetail(tno);
        Map<String, Object> checkBoardsDesc = adminTeamService.getCheckBoard(tno);

        model.addAttribute("team", team);
        model.addAttribute("checkBoardMap", checkBoardsDesc);
        model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        model.addAttribute("pageNum", pageNum);

        return "admin/teamCheckBoard";
    }

    // 팀 문의글
    @GetMapping("/teamAskBoard")
    public String teamAskBoard(@RequestParam Long tno, @RequestParam(required = false, defaultValue = "0") int pageNum, Model model){
        TeamDetailRespDto team = adminTeamService.teamDetail(tno);
        Map<String, Object> askBoardDesc = adminTeamService.getAskBoard(tno);

        model.addAttribute("team", team);
        model.addAttribute("askBoardMap", askBoardDesc);
        model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        model.addAttribute("pageNum", pageNum);

        return "admin/teamAskBoard";
    }

    // 팀 가입 멤버
    @GetMapping("/teamInMember")
    public String teamInMember(@RequestParam Long tno, @RequestParam int pageNum, Model model){
        TeamDetailRespDto team = adminTeamService.teamDetail(tno);

        model.addAttribute("team", team);
        model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        model.addAttribute("pageNum", pageNum);

        return "admin/teamInMember";
    }

    // 팀 문의글 정보
    @GetMapping("/teamAskBoardDetail")
    public String teamAskBoardDetail(@RequestParam Long tano, Model model){
        Map<String, Object> teamAskBoardAndReplyDesc = adminTeamService.getAskBoardReply(tano);

        model.addAttribute("teamAskBoardMap", teamAskBoardAndReplyDesc);

        return "admin/teamAskBoardDetail";
    }

    // 팀 문의글 댓글 작성
    @PostMapping("/teamAskBoardDetail")
    public String teamAskBoardDetailReply(@ModelAttribute TeamAskBoardReplySaveReqDto saveReqDto){

        teamAskBoardReplyService.replyAdd(saveReqDto);

        return "redirect:/admin/teamAskBoardDetail?tano=" + saveReqDto.getTano();
    }

    @GetMapping("/deleteTeamReplyRecover")
    public String deleteTeamReplyRecover(@ModelAttribute TeamAskReplyDeleteAndRecoverDto dto){
        teamAskBoardReplyService.deleteAndRecover(dto);

        return "redirect:/admin/teamAskBoardDetail?tano=" + dto.getTano();
    }




}
