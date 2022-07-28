package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.service.admin.AdminMemberService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminMemberRepository adminMemberRepository;
    private final AdminMemberService adminMemberService;


    @GetMapping("")
    public String index(@RequestParam(name="name", required = false, defaultValue = "JeongChanWoo")String name, Model model){
        model.addAttribute("name", name);
        return "admin/adminIndex";
    }

    @GetMapping("/memberList")
    public String list(Model model){
        List<Member> members = adminMemberRepository.findByMemberDelete(DeleteStatus.FALSE); // 삭제 인원은 제외 따로 출력할 예정
        model.addAttribute("members", members);
        model.addAttribute("false", DeleteStatus.FALSE);
        return "admin/memberList";
    }

    @GetMapping("/memberJoinForm")
    public String memberForm(Model model){
        model.addAttribute("memberSaveReqDto", new MemberSaveReqDto());
        model.addAttribute("male", MemberGender.MALE);
        model.addAttribute("female", MemberGender.FEMALE);
        model.addAttribute("false", DeleteStatus.FALSE);
        return "admin/memberJoinForm";
    }

    @PostMapping("/memberJoinForm")
    public String memberSubmit(@Valid MemberSaveReqDto memberSaveReqDto, BindingResult bindingResult, @RequestParam(required = false) MultipartFile file, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            model.addAttribute("male", MemberGender.MALE);
            model.addAttribute("female", MemberGender.FEMALE);
            return "admin/memberJoinForm";
        }
        memberSaveReqDto.setPhoto(file.getOriginalFilename());
        Member member = adminMemberService.joinMember(memberSaveReqDto);

        return "redirect:/admin/memberList";
    }

    @GetMapping("/memberDetail")
    public String memberDetail(@RequestParam Long mno, Model model){

        model.addAttribute("mno", mno);


         return "admin/memberDetail";
    }

    @GetMapping("/memberDelete")
    public String memberDelete(@RequestParam Long mno){

        adminMemberService.deleteMember(mno);

        return "redirect:/admin/memberList";
    }


    // 이메일 중복 체크
//    @GetMapping("/memberEmailCheck")
//    public String memberEmailCheck(@RequestParam(name="email") String email, Model model){
//        ResponseEntity<Boolean> booleanResponseEntity = ResponseEntity.ok(adminMemberService.memberEmailCheck(email));
//        String result;
//        if (Boolean.TRUE.equals(booleanResponseEntity.getBody())) {
//            result = "사용중인 이메일입니다.";
//        }else{
//            result = "사용 가능한 이메일입니다.";
//        }
//        model.addAttribute("result", result);
//        return "redirect:/admin/memberJoinForm";
//    }

}
