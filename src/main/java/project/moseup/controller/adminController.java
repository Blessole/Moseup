package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.repository.admin.AdminMemberRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class adminController {

    private final AdminMemberRepository adminMemberRepository;


    @GetMapping("")
    public String index(@RequestParam(name="name", required = false, defaultValue = "JeongChanWoo")String name, Model model){
        model.addAttribute("name", name);
        return "admin/adminIndex";
    }

    @GetMapping("/memberList")
    public String list(Model model){
        List<Member> members = adminMemberRepository.findAll();
        model.addAttribute("members", members);
        return "admin/memberList";
    }

    @GetMapping("/memberAddForm")
    public String memberForm(Model model){
        model.addAttribute("member", new Member());
        model.addAttribute("male", MemberGender.MALE);
        model.addAttribute("female", MemberGender.FEMALE);
        model.addAttribute("false", DeleteStatus.FALSE);
        return "admin/memberAddForm";
    }

    @PostMapping("/memberAddForm")
    public String memberSubmit(@ModelAttribute Member member, @RequestParam(required = false) MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        String savePath = System.getProperty("user.dir")+"\\D:\\moseupFile";





        member.setPhoto(originalFilename);
        member.setMemberDate(LocalDateTime.now());

        adminMemberRepository.save(member);

        return "redirect:/admin/memberList";
    }

    @GetMapping("/memberList")
    public String memberDetail(Model model, @RequestParam Long mno){
        Optional<Member> member = adminMemberRepository.findById(mno);
        model.addAttribute("member", member);
        return "admin/memberDetail";
    }



}
