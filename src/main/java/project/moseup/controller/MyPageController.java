package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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

import javax.validation.Valid;
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
    public String myInfo(MemberSaveReqDto memberDto, Model model, Principal principal){
        openForm(memberDto, model, principal);
        return "myPage/myInfo";
    }

    @PostMapping("/myInfo")
    public String myInfoUpdate(@Valid @ModelAttribute("myInfoDto") MemberSaveReqDto memberDto, BindingResult bindingResult, @RequestParam Long mno, Model model, Principal principal){
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

    /** MemberSaveReqDto 중복코드 */
    private void openForm(MemberSaveReqDto memberDto, Model model, Principal principal) {
        Member member = memberService.getPrincipal(principal);
        model.addAttribute("member", member);

        memberDto = memberDto.toDto(member);
        model.addAttribute("myInfoDto", memberDto);

        MemberGender[] genders = MemberGender.values();
        model.addAttribute("genders", genders);
    }
}
