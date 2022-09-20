package project.moseup.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Bankbook;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamBankbook;
import project.moseup.dto.TeamCreateReqDto;
import project.moseup.service.BankbookService;
import project.moseup.service.TeamBankbookDetailService;
import project.moseup.service.TeamBankbookService;
import project.moseup.service.TeamCreateService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.MyPageService;
import project.moseup.service.teampage.TeamMemberService;

@Controller
@RequiredArgsConstructor
public class TeamCreateController {

	private final TeamCreateService teamCreateService;
	private final MemberService memberService;
	private final TeamMemberService teamMemberService;
	private final AdminMemberService adminMemberService;
	private final BankbookService bankbookService;
	private final TeamBankbookService teamBankbookService;
	private final MyPageService myPageService;
	private final TeamBankbookDetailService teamBankbookDetailService;

   // 공용 데이터 (네비바에 들어갈 회원 정보)
   @ModelAttribute
   public void loginMember(Principal principal, Model model){
      if(principal == null){
//            throw new NoLoginException();
      }else{
         Member member = memberService.getPrincipal(principal);
         Map<String, Object> memberMap = adminMemberService.getMemberMap(member.getMno());

         model.addAttribute("memberMap", memberMap);
      }
   }


   @GetMapping("/teams/createTeam")
   public String createTeamFrom(Model model, Principal principal) {
      Member member = memberService.getMember(principal.getName());   // 로그인 세션
      List<Bankbook> myBankbook = myPageService.findBankbook(member);
      int result =myBankbook.get(0).getBankbookTotal();    // 충전 잔액
      model.addAttribute("TeamCreateReqDto", new TeamCreateReqDto());
      model.addAttribute("result", result);
      return "teams/teamCreateForm";
   }
   
   /** 팀생성시 예치금 확인 **/
   @GetMapping("/teams/checkMyWithdraw") 
   public String checkMyWithdraw(Principal principal, Model model) {
      Member member = memberService.getMember(principal.getName());   // 로그인 세션
      
      // total 금액 구하기
        List<Bankbook> myBankbook = myPageService.findBankbook(member);
        int result;
        if (myBankbook.isEmpty() || myBankbook.get(0).getBankbookTotal() == 0){
           result = -1;   // 충전내역이 없거나 잔액 0원
        } else {
           result = 1;
        }
      
      model.addAttribute("result", result);
      
      return "teams/checkMyWithdraw";
   }
   
   /** 팀 생성 **/
   @PostMapping("/teams/createTeam")
   public String  createTeam(@Valid TeamCreateReqDto teamCreateReqDto, BindingResult bindingResult, Principal principal, MultipartFile file, Model model) throws Exception{
      //@Valid : 클라이언트 측에서 넘어온 데이터를 객체에 바인딩(속성과 개체 사이 또는 연산과 기호사이와 같은 연관)할 때 유효성 검사함
      //@Valid다음에 BindingResult가 있으면 result에 오류가 담긴후 코드가 실행됨.
      if (bindingResult.hasErrors()) {   // result안에 에러가 있으면
         return "teams/teamCreateForm"; // 에러를 createMemberForm으로 가져감
      }

      //principal을 통해서 로그인한 멤버 생성
      Member member = memberService.getMember(principal.getName());
      Member findNickname = memberService.findOne(member.getMno());
      
      teamCreateReqDto.setMember(member);
      teamCreateReqDto.setTeamLeader(findNickname.getNickname());
      
      Long newTeam = teamCreateService.create(teamCreateReqDto, file);   //팀 생성
      
      //팀 멤버 테이블에 팀장 넣기
      Team team =  teamCreateService.findOne(newTeam);   //생성한 팀 조회
      teamMemberService.create(member, team);            //팀장 추가
      
      //팀장 개인통장 예치금 출금
      int result = bankbookService.withdraw(member, team);
      
      model.addAttribute("result", result);      
      
      //팀통장 입금
      TeamBankbook teamBankbook = teamBankbookService.create(team);
      
      //팀통장 디테일 생성
      teamBankbookDetailService.create(teamBankbook, team, member);
      
      model.addAttribute("tno", team.getTno());
      
      return "teams/checkTeamCreate";
   }

   /** 팀명 중복체크 **/
   @GetMapping(value = "/teams/nameChk", produces = "text/html;charset=utf-8")
   @ResponseBody   
   public String teamNameChk(String teamName) {
         String msg = "";
         List<Team> team = teamCreateService.validateDuplicateTeam(teamName);
         if(team == null) msg = "사용 가능한 팀명입니다. :)";
         else msg = "중복된 팀명입니다. :(";
         return msg;
      }

}
