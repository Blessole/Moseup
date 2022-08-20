package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        memberService.getPhotoAndNickname(principal, model);
        return "myPage/myPage";
    }

    /** 가입 스터디 목록 **/
    @GetMapping("/myTeamList")
    public String myTeamList(Model model, Principal principal, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(value="sort", defaultValue = "none", required = false) String sort){
        Member member = memberService.getPhotoAndNickname(principal, model);
//        List<Team> teams = this.myPageService.findTeam(member);

        System.out.println("과연 sort를 뭐라고 할지" + sort);

        model.addAttribute("maxPage", 5);
        model.addAttribute("teamList", myPageService.findTeamPaging(member, sort, page));
        return "myPage/myTeamList";
    }

    /** 인증글 목록 **/
    @GetMapping("/myCheckList")
    public String myCheckList(Model model, Principal principal, @RequestParam(value="page", defaultValue = "0") int page, @RequestParam(required = false) Long tno){
        Member member = memberService.getPhotoAndNickname(principal, model);
        if (tno == null) {
            model.addAttribute("checkList", myPageService.findCheckBoardPaging(member, page));
        } else {
            model.addAttribute("checkList", myPageService.findCheckBoardByTeamPaging(member, tno, page));
        }
        model.addAttribute("teamList", myPageService.findTeam(member));
        model.addAttribute("maxPage", 5);
        return "myPage/myCheckList";
    }

    /** 내 정보 보기 전 비밀번호 확인 **/
    @PreAuthorize("isAuthenticated()")
     @GetMapping("/myInfoMain")
    public String myInfoMain(Model model, Principal principal){
        memberService.getPhotoAndNickname(principal, model);
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
    public String myInfo(MemberSaveReqDto memberDto, Model model, Principal principal){
        openForm(memberDto, model, principal);
        memberService.getPhotoAndNickname(principal, model);
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
            memberService.getPhotoAndNickname(principal, model);
            return "myPage/myInfo";
        }

        memberService.update(memberDto, mno);
        return "redirect:/myPage/myInfo?mno="+mno;
    }

    /** MemberSaveReqDto 중복코드 합침*/
    private void openForm(MemberSaveReqDto memberDto, Model model, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        model.addAttribute("member", member);

        memberDto = memberDto.toDto(member);
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
}
