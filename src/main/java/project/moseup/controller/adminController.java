package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.moseup.domain.Member;
import project.moseup.repository.admin.AdminMemberRepository;

import java.util.List;

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
    public String memberAdd(){
        return "admin/memberAddForm";
    }

}
