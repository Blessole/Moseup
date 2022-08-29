package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.*;
import project.moseup.dto.*;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.service.admin.AdminFreeBoardService;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.AskBoardReplyService;
import project.moseup.service.myPage.AskBoardService;
import project.moseup.validator.CheckEmailValidator;
import project.moseup.validator.CheckNicknameValidator;
import project.moseup.validator.CheckPasswordValidator;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("admin")
@RequiredArgsConstructor
@SuppressWarnings("all")
public class AdminMemberController {

    private final AdminMemberRepository adminMemberRepository;
    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final AskBoardService askBoardService;
    private final AskBoardReplyService askBoardReplyService;
    private final AdminFreeBoardService adminFreeBoardService;


    // 유효성 검사
    private final CheckEmailValidator checkEmailValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckPasswordValidator checkPasswordValidator;


    /* 커스텀 유효성 검증 */
    // 컨트롤러가 실행 될 때 마다 검증
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkNicknameValidator);
        binder.addValidators(checkEmailValidator);
        binder.addValidators(checkPasswordValidator);
    }

    // 대시보드(시작 페이지)
    @GetMapping("")
    public String index(@RequestParam(name="name", required = false, defaultValue = "JeongChanWoo")String name,
                        Model model){
        //Member member = memberService.getPrincipal(principal);
        //model.addAttribute("member", member);

        return "admin/adminIndex";
    }

    // 회원 리스트 출력
    @GetMapping("/memberList")
    public String list(@RequestParam(required = false, defaultValue = "") String keyword,
                       @RequestParam(required = false, defaultValue = "") String orderBy,
                       @PageableDefault(size = 15, sort = "mno", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){
            //아래 코드로 실행해야 하는데 페이징이 안 먹힘 = 시작 페이지와 끝 페이지를 직접 정의해야 해서 해결책 못 찾음
            //Page<MemberRespDto> members = adminMemberService.memberKeywordList(keyword, keyword, keyword, pageable);
            // Member member = memberService.getPrincipal(principal);

            Page<Member> memberList = adminMemberService.members(orderBy, keyword, pageable);
            int startPage = Math.max(1, memberList.getPageable().getPageNumber() - 5);
            int endPage = Math.min(memberList.getTotalPages(), memberList.getPageable().getPageNumber() + 5);

            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("members", memberList);

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

    // 회원 정보 받아오기(회원가입)
    @PostMapping("/memberJoinForm")
    public String memberSubmit(@Valid MemberSaveReqDto memberSaveReqDto, BindingResult bindingResult, @RequestParam(required = false) MultipartFile file, Model model) throws IOException {
        // 유효성 검사
        if(bindingResult.hasErrors() && memberSaveReqDto.getGender() == null){
            model.addAttribute("male", MemberGender.MALE);
            model.addAttribute("female", MemberGender.FEMALE);
            return "admin/memberJoinForm";
        }
        // 파일 검사
        if(!file.getContentType().startsWith("image")){
            log.warn("이미지 파일이 아닙니다");
            return "admin/memberJoinForm";
        }

        // 파일 저장
        String fileRoot = "D:\\spring\\Moseup_image\\";	//저장될 외부 파일 경로

        String originalFileName = file.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
        log.info("extension = " + extension);

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
        log.info("savedFileName = " + savedFileName);

        // 최종 경로
        File targetFile = new File(fileRoot + savedFileName);
        log.info("targetFile = " + targetFile);

        try {
            InputStream fileStream = file.getInputStream();
            log.info("fileStream = " + fileStream);

            FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
            e.printStackTrace();
        }

        if(memberSaveReqDto.getAddress2() == null){
            memberSaveReqDto.setAddress2("");
        }

        memberSaveReqDto.setPhoto(targetFile.getAbsolutePath());
        adminMemberService.joinMember(memberSaveReqDto);

        return "redirect:/admin/memberList";
    }


    // 회원 정보 상세보기
    @GetMapping("/memberDetail")
    public String memberDetail(@RequestParam Long mno, @RequestParam(required = false, defaultValue = "0") int pageNum, Model model){
        Member member = adminMemberRepository.findById(mno).orElse(null);
        if(member == null){
            return "redirect:/admin/memberList";
        }else{
            Path path = Paths.get(member.getPhoto());

            model.addAttribute("deleteFalse", DeleteStatus.FALSE);
            model.addAttribute("fileName", path.getFileName());
            model.addAttribute("member", member);
            model.addAttribute("pageNum", pageNum);

            return "admin/memberDetail";
        }

    }

    // 회원 삭제 memberDelete (FALSE -> TRUE 변경)
    @GetMapping("/memberDelete")
    public String memberDelete(@RequestParam Long mno){

        adminMemberService.deleteMember(mno);

        return "redirect:/admin/memberDetail?mno="+mno;
    }

    // 회원 복구 memberDelete (TRUE -> FALSE 변경)
    @GetMapping("/memberRecover")
    public String memberRecover(@RequestParam Long mno){

        adminMemberService.RecoverMember(mno);

        return "redirect:/admin/memberDetail?mno="+mno;
    }

    @GetMapping("/memberBankbook")
    public String memberBankbook(@RequestParam Long mno, Model model){
        Member member = adminMemberRepository.findById(mno).orElse(null);
        if(member != null){
            model.addAttribute("member", member);
            model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        }else{
            throw new RuntimeException("회원 정보가 없습니다");
        }
        return "admin/memberBankbook";
    }

    @GetMapping("/memberFreeBoard")
    public String memberFreeBoard(@RequestParam Long mno, Model model){
        Member member = adminMemberRepository.findById(mno).orElse(null);
        if(member != null){
            model.addAttribute("member", member);
            model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        }else{
            throw new RuntimeException("회원 정보가 없습니다");
        }
        return "admin/memberFreeBoard";
    }

    @GetMapping("/memberAskBoard")
    public String memberAskBoard(@RequestParam Long mno, Model model){
        Member member = adminMemberRepository.findById(mno).orElse(null);
        if(member != null){
            model.addAttribute("member", member);
            model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        }else{
            throw new RuntimeException("회원 정보가 없습니다");
        }
        return "admin/memberAskBoard";
    }

    @GetMapping("/memberTeamAskBoard")
    public String memberTeamAskBoard(@RequestParam Long mno, Model model){
        Member member = adminMemberRepository.findById(mno).orElse(null);
        if(member != null){
            model.addAttribute("member", member);
            model.addAttribute("deleteFalse", DeleteStatus.FALSE);
        }else{
            throw new RuntimeException("회원 정보가 없습니다");
        }
        return "admin/memberTeamAskBoard";
    }

    @GetMapping("/adminAskBoards")
    public String adminAskBoards(@RequestParam(required = false, defaultValue = "") String keyword,
                                 @PageableDefault(size = 15, sort = "ano", direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model){
        Page<AskBoard> askBoards = askBoardService.askBoards(keyword, pageable);

        int startPage = Math.max(1, askBoards.getPageable().getPageNumber() - 5);
        int endPage = Math.min(askBoards.getTotalPages(), askBoards.getPageable().getPageNumber() + 5);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("askBoards", askBoards);

        return "admin/adminAskBoards";
    }

    @GetMapping("/askBoardDetail")
    public String askBoardDetail(@RequestParam(required = false) Long ano,
                                 @RequestParam(required = false, defaultValue = "0") int pageNum,
                                 Principal principal, Model model){
        Member member = memberService.getPrincipal(principal);
        if(member == null){
            return "members/loginForm";
        }

        AskBoardRespDto askBoardRespDto = askBoardService.getAskBoard(ano);

        model.addAttribute("askBoard", askBoardRespDto);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("member", member);
        model.addAttribute("replySaveDto", new AskBoardReplySaveReqDto());

        return "admin/askBoardDetail";

    }

    @PostMapping("/askBoardDetail")
    public String askBoardReplyWrite(AskBoardReplySaveReqDto replySaveDto, Model model){

        askBoardReplyService.replyAdd(replySaveDto);

        return "redirect:/admin/askBoardDetail?ano=" + replySaveDto.getAskBoard().getAno();
    }

    @GetMapping("/adminAskReplyList")
    public String adminAskReplyList(@RequestParam(required = false) Long ano, Model model){

        AskBoardRespDto askBoard = askBoardService.getAskBoard(ano);
        model.addAttribute("askBoard", askBoard);

        return "admin/adminAskReplyList";
    }

    @GetMapping("/askBoardListDetail")
    public String askBoardListDetail(@RequestParam Long arno, Model model){
        if(arno == null){
            return "admin/adminAskReplyList";
        }

        AskBoardReplyRespDto askBoardReplyRespDto = askBoardReplyService.getAskBoardReply(arno);
        model.addAttribute("reply", askBoardReplyRespDto);

        return "admin/askBoardListDetail";
    }

    @GetMapping("/freeBoards")
    public String freeBoards(@RequestParam(required = false, defaultValue = "") String keyword,
                             @PageableDefault(size = 15, sort = "fno", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model){

        Page<FreeBoard> freeBoards = adminFreeBoardService.freeBoards(keyword, pageable);

        int startPage = Math.max(1, freeBoards.getPageable().getPageNumber() - 5);
        int endPage = Math.min(freeBoards.getTotalPages(), freeBoards.getPageable().getPageNumber() + 5);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("freeBoards", freeBoards);

        return "admin/freeBoards";
    }

    @GetMapping("/freeBoardDetail")
    public String freeBoardDetail(@RequestParam Long fno,
                                  @RequestParam(required = false, defaultValue = "0") int pageNum,
                                  Model model){
        if(fno == null){
            return "admin/freeBoards";
        }
        FreeBoardRespDto freeBoardRespDto = adminFreeBoardService.freeBoardDetail(fno);

        model.addAttribute("freeBoard", freeBoardRespDto);
        model.addAttribute("pageNum", pageNum);

        return "admin/freeBoardDetail";
    }
}
