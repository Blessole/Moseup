package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardReplySaveReqDto;
import project.moseup.dto.AskBoardSaveReqDto;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.AskBoardReplyService;
import project.moseup.service.myPage.AskBoardService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("askBoard")
public class AskBoardController {

    private final MemberService memberService;
    private final AskBoardService askBoardService;
    private final AskBoardReplyService askBoardReplyService;

    @GetMapping("/askBoardList")
    public String askBoardList(Model model, Principal principal, @RequestParam(value="page", defaultValue = "0") int page){
        Member member = memberService.getPhotoAndNickname(principal, model);
//        model.addAttribute("member", member);

        model.addAttribute("askBoardList", askBoardService.findAskBoardsPaging(member, page));
        model.addAttribute("maxPage", 5);
        return "myPage/askBoardList";
    }

    @GetMapping("/askBoardForm")
    public String askBoardForm(Model model, Principal principal){
        Member member = memberService.getPhotoAndNickname(principal, model);

        model.addAttribute("askBoardForm", new AskBoardSaveReqDto());
        model.addAttribute("member", member);
        return "myPage/askBoardForm";
    }

    @PostMapping("/ask")
    public String askBoardAction(@ModelAttribute("askBoardForm") AskBoardSaveReqDto askBoardForm, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        askBoardForm.setMember(member);

        askBoardService.save(askBoardForm);

        return "redirect:/askBoard/askBoardList";
    }

    @GetMapping("/askBoardDetail")
    public String askBoardDetail(@RequestParam  Long ano, Model model, Principal principal, @RequestParam(value="page",  defaultValue = "0") int page){
        Member member = memberService.getPhotoAndNickname(principal, model);
        AskBoard askBoard = this.askBoardService.findOne(ano);

        if ( askBoardReplyService.findAll(askBoard, page) != null ) {
            Page<AskBoardReply> askBoardReplies = this.askBoardReplyService.findAll(askBoard, page);
            model.addAttribute("askBoardReplies", askBoardReplies);
        }
        model.addAttribute("askBoard", askBoard);
        model.addAttribute("member", member);
        model.addAttribute("askBoardReplyDto", new AskBoardReplySaveReqDto());

        // clean code 생각해보기 - detail & update 코드 동일!
        return "/myPage/askBoardDetail";
    }

    /** 글 수정 **/
    @GetMapping("/askBoardUpdateForm")
    public String askBoardUpdateForm(@RequestParam("ano") Long ano, Model model, Principal principal){
        Member member = memberService.getPhotoAndNickname(principal, model);
        AskBoardSaveReqDto askBoardDto = this.askBoardService.getPost(ano);
        model.addAttribute("ano", ano);
        model.addAttribute("askBoardDto", askBoardDto);
        model.addAttribute("member", member);
        return "/myPage/askBoardUpdateForm";
    }

    @PostMapping("/askUpdate")
    public String askBoardUpdate(@ModelAttribute("askBoardDto") AskBoardSaveReqDto askBoardDto, BindingResult bindingResult,
                                 @RequestParam("ano") Long ano, Model model, Principal principal){
        System.out.println("error:"+ bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            return "redirect:/askBoard/askUpdate?ano="+ano;
        }

        Member member = this.memberService.getMember(principal.getName());
        model.addAttribute("member", member);
        askBoardService.update(askBoardDto, ano);

        return "redirect:/askBoard/askBoardDetail?ano="+ano;
    }

    /** 글 삭제 **/
    @GetMapping("/askBoardDelete")
    public String askBoardDelete(@RequestParam Long ano, Model model, Principal principal) {
        Member member = memberService.getPhotoAndNickname(principal, model);
        model.addAttribute("member", member);

        askBoardService.deleteBoard(ano);
        return "redirect:/askBoard/askBoardList";
    }

    /** 댓글 작성 **/
    @PostMapping("/askBoardReplyWrite")
    public String askBoardReplyWrite(@Valid AskBoardReplySaveReqDto askBoardReplyDto, @RequestParam Long ano, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        askBoardReplyDto.setMember(member);

        AskBoard askBoard = askBoardService.findOne(ano);
        askBoardReplyDto.setAskBoard(askBoard);

        askBoardReplyService.saveAskBoardReply(askBoardReplyDto.toEntity());

        return "redirect:/askBoard/askBoardDetail?ano="+ano;
    }
}
