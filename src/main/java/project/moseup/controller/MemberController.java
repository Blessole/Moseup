package project.moseup.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.service.member.MemberService;
import project.moseup.validator.CheckRealize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
//@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final CheckRealize checkRealize;

    // 파일 업로드 경로
    @Value("${moseup.upload.path}") //application.properties의 변수
    private String uploadPath;
    
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

            // 사용 브라우저에 따라 파일이름/경로 다름
            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

            // 닉네임 폴더 생성 - 해당 위치에 폴더가 없을 경우 생성하는 코드
            String folderPath = joinForm.getNickname();
            File uploadPathFolder = new File(uploadPath, folderPath);
            if(!uploadPathFolder.exists()){
                try{
                    uploadPathFolder.mkdirs(); //폴더 생성
                } catch (Exception e){
                    e.getStackTrace(); // 에러 발생
                }
            }
            // 파일 경로 저장하기
            String uuid = UUID.randomUUID().toString()+".jpg";
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName; // 경로 + 폴더명
            Path savePath = Paths.get(saveName);
            try{
                file.transferTo(savePath);
            } catch (IOException e){
                e.printStackTrace();
            }
            //form에 저장
            joinForm.setPhoto(saveName);
        }

        try {
//            joinForm.toEntity();    8월 22일 오후 5:29 이거 한번 주석처리 해본다! 오류나나?
            memberService.join(joinForm);
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
            return "members/joinForm";
        } catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("joinFailed", e.getMessage());
            return "members/joinForm";
        }
        return "redirect:/";
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

    /** 회원 탈퇴 **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String memberDelete(SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication authentication){
        // LogoutHandler가 Authentication을 파라미터로 요구함(굳이 principal을 또 받아오지 않아도 됨)
        memberService.delete(authentication.getName());

        //탈퇴 후 로그아웃
        handler.logout(req, res, authentication);
        return "redirect:/";
    }


}
