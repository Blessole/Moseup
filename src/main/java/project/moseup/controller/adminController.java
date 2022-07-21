package project.moseup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
public class adminController {

    @GetMapping("")
    public String index(@RequestParam(name="name", required = false, defaultValue = "JeongChanWoo")String name, Model model){
        model.addAttribute("name", name);
        return "admin/adminIndex";
    }

    @GetMapping("/memberList")
    public String list(){
        return "admin/memberList";
    }

}
