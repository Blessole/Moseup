package project.moseup.controller.teampage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Bankbook;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.SecretStatus;
import project.moseup.domain.Team;
import project.moseup.domain.TeamAskBoard;
import project.moseup.domain.TeamBankbook;
import project.moseup.domain.TeamBankbookDetail;
import project.moseup.domain.TeamMember;
import project.moseup.dto.teamPage.CheckBoardDetailDto;
import project.moseup.dto.teamPage.CheckBoardDto;
import project.moseup.dto.teamPage.TeamAskBoardDeleteDto;
import project.moseup.dto.teamPage.TeamAskBoardDetailDto;
import project.moseup.dto.teamPage.TeamAskBoardDto;
import project.moseup.dto.teamPage.TeamAskBoardReplyDto;
import project.moseup.dto.teamPage.TeamAskBoardUpdateDto;
import project.moseup.dto.teamPage.TeamDetailDto;
import project.moseup.dto.teamPage.TeamMemberDetailDto;
import project.moseup.dto.teamPage.TeamMemberDto;
import project.moseup.service.BankbookService;
import project.moseup.service.TeamBankbookDetailService;
import project.moseup.service.TeamBankbookService;
import project.moseup.service.TeamCreateService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.MyPageService;
import project.moseup.service.teampage.CheckBoardService;
import project.moseup.service.teampage.TeamAskBoardReplyService;
import project.moseup.service.teampage.TeamAskBoardService;
import project.moseup.service.teampage.TeamMemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("teams")
public class TeamPageController {

	private final TeamAskBoardService teamAskBoardService;
	private final MemberService memberService;
	private final TeamAskBoardReplyService teamAskBoardReplyService;
	private final CheckBoardService checkBoardService;
	private final TeamCreateService teamCreateService;
	private final TeamMemberService teamMemberService;
	private final AdminMemberService adminMemberService;
	private final BankbookService bankbookService;
	private final MyPageService myPageService;
	private final TeamBankbookService teamBankbookService;
	private final TeamBankbookDetailService teamBankbookDetailService;

	// 공용 데이터 (네비바에 들어갈 회원 정보)
	@ModelAttribute
	public void loginMember(Principal principal, Model model){
		if(principal == null){
//				throw new NoLoginException();
		}else{
			Member member = memberService.getPrincipal(principal);
			Map<String, Object> memberMap = adminMemberService.getMemberMap(member.getMno());

			model.addAttribute("memberMap", memberMap);
		}
	}

	// 파일 업로드 경로
    @Value("${moseup.upload.path}/check") //application.properties의 변수
    private String uploadPath;

	// 팀 페이지 메인
	@GetMapping("/teamPage")
	public String teamMainPage(@RequestParam Long tno, Model model, Principal principal) {

		// 팀 정보 보여주기
		Team team = teamCreateService.findOne(tno);

		if (principal == null ){
			model.addAttribute("principal", null);
		} else {
			Member member = this.memberService.getMember(principal.getName());

			// 팀 회원 정보 가져오기
			Optional<TeamMember> teamMember = teamMemberService.findMember(team, member);
			
			// 통장에 돈이 있는지 조회
			List<Bankbook> myBankbook = myPageService.findBankbook(member);	// 통장 조회

			int deposit = team.getTeamDeposit()*10000;	//팀 예치금액
			
			int result = 0;
			
			if (myBankbook.isEmpty()) {
				result = -1;
			} else if (!myBankbook.isEmpty()) {
				if (myBankbook.get(0).getBankbookTotal() < deposit) {
					result = -1;
				} else if (myBankbook.get(0).getBankbookTotal() >= deposit) {
					result = 1;
				}
			}
			
			model.addAttribute("result", result);
			
			// 만약 존재 한다면 정보를 조회해서 전달 존재 하지 않으면 가짜정보 전달(안좋은 방법인듯...)
			if(!teamMember.isEmpty()) {
				TeamMember teamRealMember = teamMemberService.findExistMember(team, member);
				TeamMemberDetailDto teamMemberDetail = new TeamMemberDetailDto().toDto(teamRealMember);
				model.addAttribute("teamMemberDetail", teamMemberDetail);
			} else if(teamMember.isEmpty()) {
				TeamMemberDto teamFakeMember = new TeamMemberDto();
				teamFakeMember.setMember(member);
				teamFakeMember.setTeam(team);
				teamFakeMember.setTeamMemberDelete(DeleteStatus.FALSE);
				model.addAttribute("teamMemberDetail", teamFakeMember);
			}
			model.addAttribute("member", member);
			model.addAttribute("teamMember",teamMember);
			model.addAttribute("principal", 1);
		}
		
		model.addAttribute("team", team);

		return "teams/teamMain";
	}
	
	// 팀 통장 페이지
	@GetMapping("/teamBankBook")
	public String teamBankBook(@RequestParam Long tno, Model model) {
		
		Team team = teamCreateService.findOne(tno);
		
		TeamBankbook teamBankbook = teamBankbookService.findByTeam(team);
		
		// 팀 통장 자세히 찾기
		List<TeamBankbookDetail> teamBankbookDetail = teamBankbookDetailService.findTeamBankbookDetailListByTeamBankbook(teamBankbook);
		int totalMoney = teamBankbookDetail.get(teamBankbookDetail.size()-1).getTeamBankbookTotal();
		
		model.addAttribute("teamBankbookDetail", teamBankbookDetail);
		model.addAttribute("totalMoney", totalMoney);
		model.addAttribute("team", team);
		
		return "teams/teamBankbook";
	}
	
	// 팀 가입
	@GetMapping("/teamPage/joinTeam")
	public String joinTeam(@RequestParam Long tno, Model model, Principal principal) {
		
		Member member = this.memberService.getMember(principal.getName());
		Team team = teamCreateService.findOne(tno);
		
		team.updateTeamJoiner(team.getTeamJoiner() + 1);	// 팀 가입인원+1
		
		// 개인통장 예치금 출금
	    bankbookService.withdraw(member, team);
	    
	    // 팀통장 조회 후 예치금 입력
	    TeamBankbook teamBankbook = teamBankbookService.findByTeam(team);	    
	    teamBankbookDetailService.create(teamBankbook, team, member);
 	
	    TeamMemberDto teamMemberDto = new TeamMemberDto();
		teamMemberDto.setMember(member);
		teamMemberDto.setTeam(team);
			
		teamMemberService.joinTeamMember(teamMemberDto);
	  		
		return "redirect:/teams/teamPage?tno=" + tno;
	}
	
	// 팀 멤버 보여주기
	@GetMapping("/teamMemberList")
	public String teamMemberList(@RequestParam Long tno, Model model) {
		
		// 팀 정보
		Team team = teamCreateService.findOne(tno);
		TeamDetailDto teamDetail = new TeamDetailDto().toDto(team);
		
		// 인증 정보
		List<CheckBoardDetailDto> checkBoard = checkBoardService.findByTeam(team);
		
		model.addAttribute("team", teamDetail);
		model.addAttribute("checkBoard", checkBoard);

		return "teams/teamMemberList";
	}

	// 팀 페이지 문의게시판(페이징)
	@GetMapping("/teamAskBoard")
	public String teamAskBoardPage(@RequestParam Long tno, Model model, @PageableDefault(size = 10, sort = "tano", direction = Sort.Direction.DESC) Pageable pagable, Principal principal) {

		Member member = this.memberService.getMember(principal.getName());
		
		Team team = teamCreateService.findOne(tno);
		TeamDetailDto teamDetail = new TeamDetailDto().toDto(team);
		
		Page<TeamAskBoard> teamAsks = teamAskBoardService.findTeamAsksPage(team, pagable);
		int startPage = Math.max(1, teamAsks.getPageable().getPageNumber() - 4);
		int endPage = Math.min(teamAsks.getTotalPages(), teamAsks.getPageable().getPageNumber() + 5);
		
		model.addAttribute("member", member);
		model.addAttribute("team", teamDetail);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("teamAsks", teamAsks);

		return "teams/teamAskBoard";
	}

	// 팀 페이지 문의 작성 폼
	@GetMapping("/teamAskBoard/teamAskBoardWriteForm")
	public String teamAskBoardWriteForm(@RequestParam Long tno, Model model, Principal principal) {
		Member member = this.memberService.getMember(principal.getName());
		Team team = teamCreateService.findOne(tno);
		
		model.addAttribute("team", team);     
		model.addAttribute("member", member);
		model.addAttribute("teamAsk", new TeamAskBoardDto());

		return "teams/askBoardWriteForm";
	}

	// 팀 페이지 문의 작성 결과
	@PostMapping("/teamAskBoard/teamAskBoardWriteForm/createTeamAsk")
	public String createTeamAsk(@Valid TeamAskBoardDto teamAsk, BindingResult result, Principal principal, @RequestParam(required = false) String secret, @RequestParam Long tno) {

		Member member = this.memberService.getMember(principal.getName());
		Team team = teamCreateService.findOne(tno);
		
		teamAsk.setTeam(team);
		teamAsk.setMember(member);
		
		if(secret != null) {
			teamAsk.setSecret(SecretStatus.SECRET);
		} else {
			teamAsk.setSecret(SecretStatus.PUBLIC);
		}
		
		teamAskBoardService.saveTeamAskBoard(teamAsk);

		return "redirect:/teams/teamAskBoard?tno=" + tno;
	}

	// 팀 페이지 문의 글 상세보기
	@GetMapping("/teamAskBoard/teamAskBoardDetail")
	public String teamAskBoardDetail(@RequestParam Long tano, Model model, Principal principal, @RequestParam Long tno) {
		
		// 상세 보기 부분
		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);
		TeamAskBoardDetailDto teamAskOneDetail = new TeamAskBoardDetailDto().toDto(teamAskOne);
		
		Team team = teamCreateService.findOne(tno);
		
		// 조회수 올리는 부분
		teamAskBoardService.increaseReadCount(tano);
		
		// 댓글 부분
		Member loginMember = this.memberService.getMember(principal.getName());		
			
		model.addAttribute("team", team);
		model.addAttribute("teamAskOne", teamAskOneDetail);
		model.addAttribute("loginMember", loginMember);	
		model.addAttribute("teamAskReply", new TeamAskBoardReplyDto());
		
		return "teams/teamAskBoardDetail";
	}
	
	// 댓글 작성
	@PostMapping("/teamAskBoard/teamAskBoardDetail/createTeamAskReply")
	public String createTeamAskBoardReply(@Valid TeamAskBoardReplyDto teamAskReply, BindingResult result, @RequestParam Long tano, Principal principal, @RequestParam Long tno) {
		
		// 회원 정보 받아오기
		Member member = this.memberService.getMember(principal.getName());	
		teamAskReply.setMember(member);
		
		// 게시판 정보 받아오기
		TeamAskBoard teamAskBoard = teamAskBoardService.findOne(tano);
		teamAskReply.setTeamAskBoard(teamAskBoard);
		
		teamAskBoardReplyService.saveTeamAskBoardReply(teamAskReply);
		
		return "redirect:/teams/teamAskBoard/teamAskBoardDetail?tano=" + tano + "&tno=" + tno;
	}
	
	// 댓글 수정

	// 댓글 삭제


	// 문의글 수정 폼
	@GetMapping("/teamAskBoard/updateForm")
	public String teamAskBoardUpdateForm(@RequestParam Long tano, Model model, @RequestParam Long tno) {
		
		TeamAskBoard teamAskOne = teamAskBoardService.findOne(tano);
		TeamAskBoardDetailDto teamAskOneDetail = new TeamAskBoardDetailDto().toDto(teamAskOne);
		Team team = teamCreateService.findOne(tno);
		
		model.addAttribute("team", team);
		model.addAttribute("teamAskOne", teamAskOneDetail);
		
		return "teams/teamAskBoardUpdateForm";
	}
	
	// 문의글 수정 결과
	@PostMapping("/teamAskBoard/updateForm/update")
	public String teamAskBoardUpdate(@Valid TeamAskBoardUpdateDto teamAskOne, BindingResult result, @RequestParam(required = false) String secret, @RequestParam Long tano, @RequestParam Long tno) {
		
		if(secret != null) {
			teamAskOne.setSecret(SecretStatus.SECRET);
		} else {
			teamAskOne.setSecret(SecretStatus.PUBLIC);
		}
		
		teamAskBoardService.changeUpdate(teamAskOne, tano);
		
		return "redirect:/teams/teamAskBoard/teamAskBoardDetail?tano=" + tano + "&tno=" + tno;
	}
	
	// 문의글 삭제 결과
	@GetMapping("/teamAskBoard/delete")
	public String teamAskBoardDelete(@Valid TeamAskBoardDeleteDto teamAskOne, @RequestParam Long tano, Model model, @RequestParam Long tno) {
		
		teamAskOne.setTeamAskDelete(DeleteStatus.TRUE);
		
		teamAskBoardService.changeDelete(teamAskOne, tano);
		
		return "redirect:/teams/teamAskBoard?tno=" + tno;
	}

	// 팀 페이지 인증 게시판(페이징)
	@GetMapping("/teamCheckBoard")
	public String teamCheckBoardPage(Model model, @PageableDefault(size = 8, sort = "cno", direction = Sort.Direction.DESC) Pageable pagable, @RequestParam Long tno) {
		
		Team team = teamCreateService.findOne(tno);
		TeamDetailDto teamDetail = new TeamDetailDto().toDto(team);
		
		Page<CheckBoard> checkBoards = checkBoardService.findCheckBoardPage(team, pagable);
		int startPage = Math.max(1, checkBoards.getPageable().getPageNumber() - 4);
		int endPage = Math.min(checkBoards.getTotalPages(), checkBoards.getPageable().getPageNumber() + 5);
		
		// 팀 리스트 정보
		List<TeamMember> teamMemberList = teamDetail.getTeamMember();
		Map<Long, Integer> memberCheckMap = new HashMap<>();
				
		for(int i=0; i<teamMemberList.size(); i++) {
			Long oneMno = teamMemberList.get(i).getMember().getMno(); // 회원 정보
			int countCheck = checkBoardService.countByTeamAndMember(team, teamMemberList.get(i).getMember()); // 인증 횟수
			memberCheckMap.put(oneMno, countCheck);
		}
		
		model.addAttribute("photoList", checkBoardService.findByTeam(team));
		model.addAttribute("team", teamDetail);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("checkBoards", checkBoards);
		model.addAttribute("memberCheckMap", memberCheckMap);
		
		return "teams/teamCheckBoard";
	}
	
	// 인증 게시판 작성
	@GetMapping("/teamCheckBoard/checkBoardWriteForm")
	public String teamCheckBoardWriteForm(@RequestParam Long tno, Model model, Principal principal) {
		Member member = this.memberService.getMember(principal.getName());
		Team team = teamCreateService.findOne(tno);
		
		model.addAttribute("team", team);    
		model.addAttribute("member", member);
		model.addAttribute("teamCheck", new CheckBoardDto());

		return "teams/checkBoardWriteForm";
	}
	
	// 인증 게시판 작성 결과
	@PostMapping("/teamCheckBoard/checkBoardWriteForm/createTeamCheck")
	public String createTeamAsk(@Valid CheckBoardDto teamCheck, BindingResult result, @RequestParam(required = false) MultipartFile checkPhoto, Principal principal, @RequestParam Long tno) throws IOException {

		Member member = this.memberService.getMember(principal.getName());
		Team team = teamCreateService.findOne(tno);
		
		teamCheck.setTeam(team);
		teamCheck.setMember(member);
		
		// 파일 업로드
		if(checkPhoto.getContentType().startsWith("image") == false) {
			return "redirect:/";
		}
		
		// 사진 값이 있을 때만 처리
		if(checkPhoto.isEmpty()) {
			teamCheck.setCheckPhoto("empty");
		} else if(!checkPhoto.isEmpty()) {
			 String originalName = checkPhoto.getOriginalFilename();
			 String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
			 
			 // 이미지 저장할 폴더 생성
			 String folderPath = teamCheck.getMember().getEmail();
			 File uploadPathFolder = new File(uploadPath, folderPath);
			 
			 if(uploadPathFolder.exists() == false) {
				 uploadPathFolder.mkdirs();
			 }
			 
			 // 저장할 경로 지정
			 String uuid = UUID.randomUUID().toString();
			 String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
			 Path savePath = Paths.get(saveName);
			 try{
				 checkPhoto.transferTo(savePath);
	         } catch (IOException e){
	             e.printStackTrace();
	         }
			 
			 teamCheck.setCheckPhoto(saveName);
		}
		
		checkBoardService.saveCheckBoard(teamCheck);

		return "redirect:/teams/teamCheckBoard?tno=" + tno;
	}
	
	// 인증 완료시 로직
	@GetMapping("/completeCheck")
	public String completeCheck(@RequestParam Long tno, Model model, Principal principal) {
		
		Member member = this.memberService.getMember(principal.getName());
		Team team = teamCreateService.findOne(tno);
		TeamBankbook teamBankbook = teamBankbookService.findByTeam(team);
		
		// 팀 통장 출금
		teamBankbookDetailService.withdraw(teamBankbook, team, member);
		
		// 개인통장 찾아서 넣기
		bankbookService.deposit(member, team);
			
		return "redirect:/teams/teamPage?tno=" + tno;
	}
	
	// 인증 게시판 상세보기
	@GetMapping("/teamCheckBoard/teamCheckBoardDetail")
	public String teamCheckBoardDetail(@RequestParam Long cno, Model model, Principal principal, @RequestParam Long tno) {
		
		// 상세 보기 부분
		CheckBoard checkOne = checkBoardService.findOne(cno);
		CheckBoardDetailDto checkOneDetail = new CheckBoardDetailDto().toDto(checkOne);
		
		Team team = teamCreateService.findOne(tno);
		
		// 조회수 올리는 부분
		checkBoardService.increaseReadCount(cno);
		
		// 사진 경로 src에 맞게 설정
		String photo = checkOne.getCheckPhoto();
        int index = photo.indexOf("images");
        String realPhoto = photo.substring(index - 1);
        
        model.addAttribute("realPhoto", realPhoto);
		model.addAttribute("team", team);  
		model.addAttribute("checkOne", checkOneDetail);
		
		return "teams/teamCheckBoardDetail";
	}

}
