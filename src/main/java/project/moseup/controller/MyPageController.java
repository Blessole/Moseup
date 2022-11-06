package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.moseup.domain.*;
import project.moseup.dto.*;
import project.moseup.exception.NoLoginException;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.MyPageService;
import project.moseup.service.teampage.TeamMemberService;
import project.moseup.validator.CheckEmailValidator;
import project.moseup.validator.CheckNicknameValidator;
import project.moseup.validator.CheckPasswordValidator;
import project.moseup.validator.CheckRealize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {

    private final MemberService memberService;
    private final MyPageService myPageService;
    private final AdminMemberService adminMemberService;

    // 유효성 검사
    private final CheckRealize checkRealize;

    // 공용 데이터 (사이드바에 들어갈 회원 정보)
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

    /** 가입 스터디 목록 **/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myTeamList")
    public String myTeamList(Model model, Principal principal, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value="sort", defaultValue = "none", required = false) String sort){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);

        Page<Team> teamList = myPageService.findTeamPaging((Member) map.get("member"), sort, page);
        // 팀 번호 매기기
        int rowNum = teamList.getNumberOfElements();
        int[] rowList = new int[rowNum];
        for (int row = 0; row<rowNum; row++){
            rowList[row] = row+1;
        }

        model.addAttribute("maxPage", 5);
        model.addAttribute("teamList", teamList);
        model.addAttribute("map", map);
        model.addAttribute("rowNum", rowList);
        return "myPage/myTeamList";
    }

    /** 인증글 목록 **/
    @GetMapping("/myCheckList")
    public String myCheckList(Model model, Principal principal, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(required = false) Long tno){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        Member member = (Member) map.get("member");

        if (tno == null) {
            model.addAttribute("checkList", myPageService.findCheckBoardPaging(member, page));
        } else {
            model.addAttribute("checkList", myPageService.findCheckBoardByTeamPaging(member, tno, page));
        }
        model.addAttribute("photoList", myPageService.findCheckBoardList(member));
        model.addAttribute("teamList", myPageService.findTeam(member));
        model.addAttribute("maxPage", 5);
        model.addAttribute("map", map);
        return "myPage/myCheckList";
    }

    /** 내 정보 보기 전 비밀번호 확인 **/
    @PreAuthorize("isAuthenticated()")
     @GetMapping("/myInfoMain")
    public String myInfoMain(Model model, Principal principal){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        model.addAttribute("map", map);
        return "myPage/myInfoMain";
    }

    /** 비밀번호 체크 **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/myInfoMain")
    public String checkPassword(@NotEmpty String password, Principal principal){
        Boolean result = memberService.checkPassword(password, principal.getName());
        if (!result) {
            return "redirect:/myPage/myInfoMain?error";
        }
            Member member = memberService.getMember(principal.getName());
            return "redirect:/myPage/myInfo?mno="+member.getMno();
        }

    /** 내 정보 수정 폼 열기 **/
    @GetMapping("/myInfo")
    public String myInfo(Model model, Principal principal){
        openForm(model, principal);
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        model.addAttribute("map", map);
        return "myPage/myInfo";
    }

    /** 마이페이지용 닉네임 중복체크 **/
    @GetMapping(value = "/checkRealize", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String checkRealizeInMyPage(String value, Principal principal){
        String msg = checkRealize.nicknameCheckInMyPage(value, principal);
        return msg;
    }

    /** 내 정보 수정 액션 **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/myInfo")
    public String myInfo(@ModelAttribute("myInfoDto") MemberSaveReqDto memberDto, BindingResult bindingResult, @RequestParam(required = false, name="file") MultipartFile file, @RequestParam(name = "mno") Long mno, Model model, Principal principal){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);

        // 유효성 검사
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            openForm(model, principal);
            return "myPage/myInfo";
        }

        // 파일 업로드 시작
        String originPath = ((Member)map.get("member")).getPhoto();
        if(!file.isEmpty()){
            if(file.getContentType().startsWith("image")==false){
                log.info("이미지 파일만 올리세요 ㅠ");
                return "redirect:/myPage/myInfo?mno="+mno;
            }
            // 기존 사진 삭제
            if(originPath != null ){
                   File deleteFile = new File(originPath);
                   if (deleteFile.exists()){
                       //파일 삭제
                       deleteFile.delete();
                       log.info("기존 사진을 삭제했습니다.");
                   } else {
                       log.info("사진이 존재하지 않습니다.");
                   }
            }
            // 새로운 사진 저장 (폴더 없으면 폴더 생성)
            String folderPath = "memberPhotos";
            String personalPath = ((Member)map.get("member")).getEmail();
            String saveName = memberService.makeFolderAndFileName(file, folderPath, personalPath);
            // form에 저장
            memberDto.setPhoto(saveName);
        } else if (file.isEmpty()){
            if(originPath != null ) {
                memberDto.setPhoto(originPath);
            }
        }
        try{
            memberService.update(memberDto, mno);
            model.addAttribute("result", "1");
            model.addAttribute("mno", mno);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("result", "0");
        }
        model.addAttribute("map", map);
        return "myPage/updateMember";
    }

    /** MemberSaveReqDto 중복코드 합침*/  // 서비스단으로 빼야겠다 이거
    private void openForm(Model model, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        model.addAttribute("member", member);

        MemberSaveReqDto memberDto = new MemberSaveReqDto();
        memberDto.toDto(member);
//        log.info("memberDto 이메일 : " + memberDto.getEmail());
        model.addAttribute("myInfoDto", memberDto);

        // 주소 다시 나누기
        String originAdd = member.getAddress();
        int addIndex = originAdd.indexOf(",");
        String add1 = originAdd.substring(0, addIndex-1);
        String add2 = originAdd.substring(addIndex+2);
        model.addAttribute("add1", add1);
        model.addAttribute("add2", add2);

        MemberGender[] genders = MemberGender.values();
        model.addAttribute("genders", genders);
    }

    @GetMapping("/myBankbook")
    public String myBankbookList(Principal principal, Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        Member member = (Member) map.get("member");
        Page<Bankbook> bankbook = myPageService.findBankbookPaging(member, page);

        // total 금액 구하기
        List<Bankbook> myBankbook = myPageService.findBankbook(member);
        int originMoney;
        if (myBankbook.isEmpty()){
            originMoney = 0;
        } else {
            originMoney = myBankbook.get(0).getBankbookTotal();
        }

        // 참고할 찬우 코드 - total 금액 구하는 방법 : size 구해서 -1
//        int bankbookSize = member.getBankbooks().size();
//        bankbookTotal = member.getBankbooks().get(bankbookSize - 1).getBankbookTotal();

        model.addAttribute("myTotal", originMoney);
        model.addAttribute("bankbookDto", bankbook);
        model.addAttribute("maxPage", 20);
        model.addAttribute("map", map);
        return "myPage/myBankbook";
    }

    @GetMapping("/myBankbook/moneyCharge")
    public String moneyCharge(@ModelAttribute("chargeDto") BankbookSaveReqDto bankbookDto, Principal principal, Model model){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        List<Bankbook> myBankbook = myPageService.findBankbook((Member) map.get("member"));
        if (myBankbook.isEmpty()){
            model.addAttribute("myTotal", 0);
        } else {
            model.addAttribute("myTotal", myBankbook.get(0).getBankbookTotal());
        }        model.addAttribute("map", map);

        return "myPage/moneyCharge";
    }

    @PostMapping("/moneyChargeAction")
    public String moneyChargeAction(BankbookSaveReqDto bankbookDto, BindingResult bindingResult, Principal principal, Model model){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        Member member = (Member) map.get("member");
        int result = 0;
        // 유효성 검사
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            List<Bankbook> myBankbook = myPageService.findBankbook(member);
            model.addAttribute("map", map);
            if (myBankbook.isEmpty()){
                model.addAttribute("myTotal", 0);
            } else {model.addAttribute("myTotal", myBankbook);}
            result = 0;
        }

//        model.addAttribute("member", member);

        List<Bankbook> myBankbook = myPageService.findBankbook(member);
        int originMoney;
        if (myBankbook.isEmpty()){
            originMoney = 0;
        } else {
            originMoney = myBankbook.get(0).getBankbookTotal();
        }
        int newTotal = bankbookDto.getBankbookDeposit() + originMoney;

        bankbookDto.setMember(member);
        bankbookDto.setDealList("머니 충전:-D");
        bankbookDto.setBankbookDate(LocalDateTime.now());
        bankbookDto.setBankbookWithdraw(0);
        bankbookDto.setBankbookDeposit(bankbookDto.getBankbookDeposit());
        bankbookDto.setBankbookTotal(newTotal);

        try {
            myPageService.charge(bankbookDto);
            result = 1;
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("moneyChargeFailed", "아쉽게도 충전 실패다!");
            result=0;
        } catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("moneyChargeFailed", "아쉽게도 충전 실패다!");
            result = 0;
        }
        model.addAttribute("map", map);
        model.addAttribute("result", result);
        return "myPage/moneyChargeAction";
    }

    /** 찜 목록 불러오기 **/
    @GetMapping("/myLikeList")
    public String myLikeList(Principal principal, Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        Member member = (Member) map.get("member");

        model.addAttribute("map", map);
        model.addAttribute("likeList", myPageService.getMyLikeList(member, page));
        model.addAttribute("maxPage", 8);
        return "myPage/myLikeList";
    }

    /** 찜 기능 **/
    @GetMapping(value = "/likeUnlike", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String likeUnlike(Long tno, String name, LikeSaveReqDto likesDto, Model model, Principal principal){

        System.out.println("team : "+ tno);
        String result="";
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        model.addAttribute("map", map);

        likesDto.setTeam(myPageService.getTeam(tno).get());
        likesDto.setMember((Member) map.get("member"));
        Likes likes = likesDto.toEntity();
        if (name.equals("unLike")) {
            myPageService.likeUnlike(name, likes);
            result = "0";
        } else {
            myPageService.likeUnlike(name, likes);
            result = "1";
        }
        System.out.println("result : " + result);
        return result;
    }


    /** 회원 탈퇴 **/
    @GetMapping("/deleteMember")
    public String deleteMember(Model model, Principal principal, SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication authentication){
        int result;

        // 진행중인 팀 여부 확인하기
        List<Team> ing = myPageService.beforeDelete(memberService.getMember(principal.getName()));
        System.out.println("ing : " + ing);
        if (ing.isEmpty()){
            // LogoutHandler가 Authentication을 파라미터로 요구함(굳이 principal을 또 받아오지 않아도 됨)
            memberService.delete(authentication.getName());
            myPageService.updateTeamMember(memberService.getMember(principal.getName()));

            //탈퇴 후 로그아웃
            handler.logout(req, res, authentication);
            log.info("회원 탈퇴 완료");
            result = 1;
        } else {
            Map<String, Object> map = memberService.getPhotoAndNickname(principal);
            model.addAttribute("map", map);
            log.info("회원 탈퇴 실패");
            result = 0;
        }
        model.addAttribute("result", result);
        return "myPage/deleteMember";
    }

    /** 팀 탈퇴 기능 **/
    @GetMapping("/teamMemberDelete")
    public String teamMemberDelete(@Param("tno") Long tno, Principal principal, Model model){
        log.info("과연 : " + tno);

        Member member = memberService.getMember(principal.getName());
        int result = myPageService.deleteTeamMember(tno, member);

        System.out.println("result : " + result);
        model.addAttribute("result", result);
        return "myPage/teamMemberDelete";
    }
}
