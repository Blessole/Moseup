package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.TeamCreateReqDto;
import project.moseup.service.TeamCreateService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;
import project.moseup.service.teampage.TeamMemberService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TeamCreateController {

	private final TeamCreateService teamCreateService;
	private final MemberService memberService;
	private final TeamMemberService teamMemberService;
	private final AdminMemberService adminMemberService;

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
	public String createTeamFrom(Model model) {
		model.addAttribute("TeamCreateReqDto", new TeamCreateReqDto());
		return "teams/teamCreateForm";
	}

	//	team 생성
	@PostMapping("/teams/createTeam")
	public String  createTeam(@Valid TeamCreateReqDto teamCreateReqDto, BindingResult result, Principal principal, MultipartFile file) throws Exception{
		//@Valid : 클라이언트 측에서 넘어온 데이터를 객체에 바인딩(속성과 개체 사이 또는 연산과 기호사이와 같은 연관)할 때 유효성 검사함
		//@Valid다음에 BindingResult가 있으면 result에 오류가 담긴후 코드가 실행됨.
		if (result.hasErrors()) {	// result안에 에러가 있으면
			return "teams/teamCreateForm"; // 에러를 createMemberForm으로 가져감
		}

		//principal을 통해서 로그인한 멤버 생성
		Member member = memberService.getMember(principal.getName());
		Member findNickname = memberService.findOne(member.getMno());
		
		teamCreateReqDto.setMember(member);
		teamCreateReqDto.setTeamLeader(findNickname.getNickname());

		
		Long newTeam = teamCreateService.create(teamCreateReqDto, file);	//팀 생성
		
		//팀 멤버 테이블에 팀장 넣기
		Team team =  teamCreateService.findOne(newTeam);	//생성한 팀

		teamMemberService.create(member, team); //팀멤버 생성

		return "redirect:/";	// 초기화면으로 돌아감
	}

	// 팀명 중복체크
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
