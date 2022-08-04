package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.moseup.domain.Team;
import project.moseup.repository.admin.AdminTeamRepository;
import project.moseup.service.admin.AdminTeamService;

@Controller
@Log4j2
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminTeamController {

    private final AdminTeamRepository adminTeamRepository;
    private final AdminTeamService adminTeamService;

    @GetMapping("/teamList")
    public String teamList(@RequestParam(required = false, defaultValue = "")String keyword, Model model,
                           @PageableDefault(size = 10, sort = "tno", direction = Sort.Direction.DESC) Pageable pageable){

        Page<Team> teams = adminTeamRepository.findByTeamNameContaining(keyword, pageable);

        int startPage = Math.max(1, teams.getPageable().getPageNumber() - 5);
        int endPage = Math.min(teams.getTotalPages(), teams.getPageable().getPageNumber() + 5);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("teams", teams);

        return "admin/teamList";
    }

}
