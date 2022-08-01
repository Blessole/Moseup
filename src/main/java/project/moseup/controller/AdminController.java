package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.MemberRespDto;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.validator.CheckEmailValidator;
import project.moseup.validator.CheckNicknameValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminMemberRepository adminMemberRepository;
    private final AdminMemberService adminMemberService;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckNicknameValidator checkNicknameValidator;

    /* 커스텀 유효성 검증 */
    // 컨트롤러가 실행 될 때 마다 검증
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkNicknameValidator);
        binder.addValidators(checkEmailValidator);
    }

    // 대시보드(시작 페이지)
    @GetMapping("")
    public String index(@RequestParam(name="name", required = false, defaultValue = "JeongChanWoo")String name, Model model){
        model.addAttribute("name", name);
        return "admin/adminIndex";
    }

    // 회원 리스트 출력
    @GetMapping("/memberList")
    public String list(Model model){
        List<Member> members = adminMemberRepository.findByMemberDelete(DeleteStatus.FALSE); // 삭제 인원은 제외 따로 출력할 예정
        model.addAttribute("members", members);
        model.addAttribute("false", DeleteStatus.FALSE);
        return "admin/memberList";
    }

    // 회원 가입 폼 이동
    @GetMapping("/memberJoinForm")
    public String memberForm(Model model){
        model.addAttribute("memberSaveReqDto", new MemberSaveReqDto());
        model.addAttribute("male", MemberGender.MALE);
        model.addAttribute("female", MemberGender.FEMALE);
        model.addAttribute("false", DeleteStatus.FALSE);
        return "admin/memberJoinForm";
    }

    // 회원 정보 받아오기
    @PostMapping("/memberJoinForm")
    public String memberSubmit(@Valid MemberSaveReqDto memberSaveReqDto, BindingResult bindingResult, @RequestParam(required = false) MultipartFile file, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            model.addAttribute("male", MemberGender.MALE);
            model.addAttribute("female", MemberGender.FEMALE);

            return "admin/memberJoinForm";
        }
        // 파일 저장
        memberSaveReqDto.setPhoto(file.getOriginalFilename());


        MemberRespDto memberRespDto = adminMemberService.joinMember(memberSaveReqDto);

        return "redirect:/admin/memberList";
    }

    // 회원 정보 상세보기
    @GetMapping("/memberDetail")
    public String memberDetail(@RequestParam Long mno, Model model){
        Member member = adminMemberRepository.findById(mno).orElse(null);
        if(member == null){
            return "redirect:/admin/memberList";
        }else{
            model.addAttribute("member", member);
            model.addAttribute("mno", mno);

            return "admin/memberDetail";
        }

    }

    // 회원 삭제 delete -> TRUE 변경
    @GetMapping("/memberDelete")
    public String memberDelete(@RequestParam Long mno){

        adminMemberService.deleteMember(mno);

        return "redirect:/admin/memberList";
    }


}
