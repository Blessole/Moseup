package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.KakaoLoginForm;
import project.moseup.dto.Mail;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.exception.NoLoginException;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.service.member.MailService;
import project.moseup.service.member.MemberService;
import project.moseup.validator.CheckRealize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final CheckRealize checkRealize;
    private final MailService mailService;
    private final AdminMemberService adminMemberService;

    // 공용 데이터 (네비바에 들어갈 회원 정보)
    @ModelAttribute
    public void loginMember(Principal principal, Model model){
        if(principal == null){
//            throw new NoLoginException();
        }else{
            Member member = memberService.getPrincipal(principal);
            Map<String, Object> memberMap = adminMemberService.getMemberMap(member.getMno());

            model.addAttribute("memberMap", memberMap);
        }
    }

    // 회원가입
    @GetMapping("/joinForm")
    public String joinForm(Model model){
        //해당 enum의 모든 정보를 배열로 반환 [MALE, FEMALE]
        MemberGender[] genders = MemberGender.values();
        model.addAttribute("genders", genders);
        model.addAttribute("joinForm", new MemberSaveReqDto());
        return "members/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid MemberSaveReqDto joinForm, BindingResult bindingResult, @RequestPart(required = false) MultipartFile file, Model model) throws IOException {
        System.out.println("error:"+ bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            MemberGender[] genders = MemberGender.values();
            model.addAttribute("genders", genders);
            model.addAttribute("joinForm", new MemberSaveReqDto());
            return "redirect:/members/joinForm";
        }

        // 파일 업로드 시작
        // 이미지 파일만 업로드 가능하도록 제한하기
        System.out.println("컨텐트 타입2 : "+ file.getContentType());
        if (file.isEmpty()) { // 프로필사진이 등록되지 않은 경우
            joinForm.setPhoto("C:\\DevSpace\\Project\\Moseup\\src\\main\\resources\\static\\images\\profile.png");
        } else if (!file.isEmpty()){  // 프로필사진이 등록된 경우
            if(file.getContentType().startsWith("image") == false){
                System.out.println("MC - 이미지 파일만 올려~");
                System.out.println("컨텐트 타입 : "+ file.getContentType());
                return "redirect:/members/joinForm";
            }

            // 이메일 폴더 생성 - 해당 위치에 폴더가 없을 경우 생성하는 코드
            String folderPath = "memberPhotos";
            String personalPath = joinForm.getEmail();
            String saveName = memberService.makeFolderAndFileName(file, folderPath, personalPath);
            //form에 저장
            joinForm.setPhoto(saveName);
        }

        try {
            memberService.join(joinForm);
            return "members/joinMember";
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
            return "members/joinForm";
        } catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "members/joinForm";
        }
    }

    /** 중복체크 **/
    @GetMapping(value = "/checkRealize", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String checkRealize(String value){
        String msg = "";
        if (value.contains("@")) {
            msg = checkRealize.emailCheck(value);
        } else {
            msg = checkRealize.nicknameCheck(value);
        }
        return msg;
    }

    /** 비밀번호 체크 **/
    @GetMapping(value = "/passwordRealize", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String passwordRealize(String password1, String password2){
        String msg = checkRealize.passwordCheck(password1, password2);
        return msg;
    }

    @GetMapping("/login")
    public String loginForm(){
        return "members/loginForm";
    }

    // KAKAO Login API
    @PostMapping("/kakao-login")
    public String loginWithKakao(KakaoLoginForm form){
//        log.info("카카오 로그인 정보 : "+form);
        Member member = form.toEntity();
        Member savedMember = memberService.loginWithKakao(member);

        if (savedMember != null){

        }
        return "redirect:/";
    }

    /** 아이디 찾기 **/
    @GetMapping("/findID")
    public String findID(@ModelAttribute("memberDto") MemberSaveReqDto memberSaveReqDto){
        return "members/findID";
    }
    @PostMapping("/findID")
    public String findIDAction(@ModelAttribute MemberSaveReqDto memberSaveReqDto, Model model){
        int result = 0;
        Member member = memberService.findByName(memberSaveReqDto);
        if (member != null){
            result = 1;
            model.addAttribute("member", member);
        } else {
            result = -1;
        } model.addAttribute("result", result);
        return "members/findIDAction";
    }


    /** 비밀번호 찾기 **/
    @GetMapping("/findPW")
    public String findPW(@ModelAttribute("memberDto") MemberSaveReqDto memberSaveReqDto){
        return "members/findPW";
    }
    @PostMapping("/findPW")
    public String findPWAction(@ModelAttribute MemberSaveReqDto memberSaveReqDto, Model model){
        log.info("findPW 진입");
        log.info("이메일 : " + memberSaveReqDto.getEmail());

        int result = 0;
        Member member = memberService.findByEmail(memberSaveReqDto);
        if (member != null){
            result = 1;

            // 임시 비밀번호 생성
            String tmpPw = memberService.getTmpPassword();

            // 임시 비밀번호 저장
            memberService.updatePassword(tmpPw, member.getEmail());

            // 메일 생성 & 전송
            Mail mail = mailService.createMail(tmpPw, member.getEmail());
            mailService.sendMail(mail);
            
            log.info("임시 비밀번호 전송 완료");
        } else {
            result = -1;
        } model.addAttribute("result", result);
        return "members/findPWAction";
    }
}
