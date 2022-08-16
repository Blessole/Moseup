package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.moseup.domain.CheckBoard;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Team;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.MyPageService;
import project.moseup.validator.CheckEmailValidator;
import project.moseup.validator.CheckNicknameValidator;
import project.moseup.validator.CheckPasswordValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {

    private final MemberService memberService;
    private final MyPageService myPageService;

    // 유효성 검사
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckPasswordValidator checkPasswordValidator;

    // Spring Validator 사용 시
    // @Valid annotation으로 검증이 필요한 객체를 가져오기 전에 수행할 method를 지정
    @InitBinder
    public void validatorBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(checkNicknameValidator);
        webDataBinder.addValidators(checkEmailValidator);
        webDataBinder.addValidators(checkPasswordValidator);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myPage")
    public String myPage(Model model, Principal principal) {
        Member member = memberService.getPrincipal(principal);
        model.addAttribute("member", member);
        return "myPage/myPage";
    }

    @GetMapping("/myTeamList")
    public String myTeamList(Model model, Principal principal){
        Member member = memberService.getPrincipal(principal);
        List<Team> teams = this.myPageService.findTeam(member);

        model.addAttribute("teamList", teams);
        model.addAttribute("member",member);

        return "myPage/myTeamList";
    }

    @GetMapping("/myCheckList")
    public String myCheckList(Model model, Principal principal){
        Member member = memberService.getPrincipal(principal);
        List<CheckBoard> checkBoards = this.myPageService.findCheckBoard(member);
        model.addAttribute("checkList", checkBoards);
        model.addAttribute("member", member);
        return "myPage/myCheckList";
    }

    /** 내 정보 보기 전 비밀번호 확인 **/
    @PreAuthorize("isAuthenticated()")
     @GetMapping("/myInfoMain")
    public String myInfoMain(Model model, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("member", member);
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
            Member member = memberService.getPrincipal(principal);
            return "redirect:/myPage/myInfo?mno="+member.getMno();
        }
//        Member member = this.memberService.getMember(principal.getName());
//        String origin = member.getPassword();
//        String msg = checkRealize.passwordCheck(origin, scan);
//        return msg;

    /** 내 정보 수정 폼 열기 **/
    @GetMapping("/myInfo")
    public String myInfo(MemberSaveReqDto memberDto, Model model, Principal principal){
        openForm(memberDto, model, principal);
        return "myPage/myInfo";
    }

    /** 내 정보 수정 액션 **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/myInfo")
    public String myInfoUpdate(@Valid @ModelAttribute("myInfoDto") MemberSaveReqDto memberDto, BindingResult bindingResult, @RequestParam Long mno, Model model, RedirectAttributes ra, Principal principal){
        System.out.println("memberDto email : " + memberDto.getEmail());
        // 유효성 검사
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            openForm(memberDto, model, principal);
            return "myPage/myInfo";
        }

        memberService.update(memberDto, mno);
        return "redirect:/myPage/myInfo?mno="+mno;
    }

    /** MemberSaveReqDto 중복코드 합침*/
    private void openForm(MemberSaveReqDto memberDto, Model model, Principal principal) {
        Member member = memberService.getPrincipal(principal);
        model.addAttribute("member", member);

        memberDto = memberDto.toDto(member);
        model.addAttribute("myInfoDto", memberDto);

        // 사진 경로 local에서 project용으로 변경
        String photo = member.getPhoto();
        int index = photo.indexOf("images");
        String realPhoto = photo.substring(index-1);
        model.addAttribute("photoPath", realPhoto);

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
}
