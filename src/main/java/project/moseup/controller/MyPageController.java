package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.moseup.domain.*;
import project.moseup.dto.BankbookRespDto;
import project.moseup.dto.BankbookSaveReqDto;
import project.moseup.dto.CheckBoardRespDto;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.exception.NoLoginException;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.MyPageService;
import project.moseup.validator.CheckEmailValidator;
import project.moseup.validator.CheckNicknameValidator;
import project.moseup.validator.CheckPasswordValidator;
import project.moseup.validator.CheckRealize;

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

    // 유효성 검사
    private final CheckRealize checkRealize;

    /** 가입 스터디 목록 **/
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myTeamList")
    public String myTeamList(Model model, Principal principal, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value="sort", defaultValue = "none", required = false) String sort){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);

        model.addAttribute("maxPage", 5);
        model.addAttribute("teamList", myPageService.findTeamPaging((Member) map.get("member"), sort, page));
        model.addAttribute("map", map);
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
    public String myInfoUpdate(@ModelAttribute("myInfoDto") MemberSaveReqDto memberDto, BindingResult bindingResult, @RequestParam(required = false, name="file") MultipartFile file, @RequestParam(name = "mno") Long mno, Model model, Principal principal){
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
            String folderPath = ((Member)map.get("member")).getEmail();
            String saveName = memberService.makeFolderAndFileName(file, folderPath);
            // form에 저장
            memberDto.setPhoto(saveName);
        } else if (file.isEmpty()){
            if(originPath != null ) {
                memberDto.setPhoto(originPath);
            }
        }

        memberService.update(memberDto, mno);
        model.addAttribute("map", map);
        return "redirect:/myPage/myInfo?mno="+mno;
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

    @GetMapping("/moneyCharge")
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
            } else model.addAttribute("myTotal", myBankbook);
            return "myPage/moneyCharge";
        }

        model.addAttribute("member", member);

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

        myPageService.charge(bankbookDto);
        model.addAttribute("map", map);

        return "redirect:/myPage/myBankbook";
    }

    @GetMapping("/myLikeList")
    public String myLikeList(Principal principal, Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        Member member = (Member) map.get("member");

        model.addAttribute("map", map);
        model.addAttribute("likeList", myPageService.getMyLikeList(member, page));
        model.addAttribute("maxPage", 10);
        return "myPage/myLikeList";
    }
}
