package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.dto.MemberRespDto;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.service.admin.AdminMemberService;
import project.moseup.validator.CheckEmailValidator;
import project.moseup.validator.CheckNicknameValidator;
import project.moseup.validator.CheckPasswordValidator;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminMemberRepository adminMemberRepository;
    private final AdminMemberService adminMemberService;
    // 유효성 검사
    private final CheckEmailValidator checkEmailValidator;
    private final CheckNicknameValidator checkNicknameValidator;
    private final CheckPasswordValidator checkPasswordValidator;

    @Value("${moseup.upload.path}")
    private String uploadPath;

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
    public String index(@RequestParam(name="name", required = false, defaultValue = "JeongChanWoo")String name, Model model){
        model.addAttribute("name", name);
        return "admin/adminIndex";
    }

    // 회원 리스트 출력
    @GetMapping("/memberList")
    public String list(@RequestParam(required = false, defaultValue = "")String keyword, Model model){
        if(StringUtils.isEmpty(keyword)){
            // 검색 단어가 없을 땐 전체 데이터 조회
            // 엔티티 데이터를 그대로 주지 않고 DTO 변환후 넘기기
            List<MemberRespDto> members = adminMemberService.memberListAll();
            model.addAttribute("members", members);
        }else{
            List<MemberRespDto> memberList = adminMemberService.memberSearch(keyword);
            model.addAttribute("members", memberList);
        }

        //List<Member> members = adminMemberRepository.findByMemberDelete(DeleteStatus.FALSE);

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
        // 유효성 검사
        if(bindingResult.hasErrors()){
            model.addAttribute("male", MemberGender.MALE);
            model.addAttribute("female", MemberGender.FEMALE);
            return "admin/memberJoinForm";
        }

        // 파일 처리
        if(!file.getContentType().startsWith("image")){
            log.warn("이미지 파일이 아닙니다");
            return "admin/memberJoinForm";
        }
        // 브라우저 마다 저장되는 이름이 다를 수 있음
        String originalFilename = file.getOriginalFilename();
        String fileName = null;
        if (originalFilename != null) {
            fileName = originalFilename.substring(originalFilename.lastIndexOf("//")+1);
        }
        log.info("fileName = " + fileName);

        // 날짜 폴더 생성
        String folderPath = makeFolder();
        //UUID
        String uuid = UUID.randomUUID().toString();
        //저장할 파일 이름 중간에 "_"를 이용하여 구분
        String saveName = uploadPath + File.separator + folderPath +File.separator + uuid + "_" + fileName;

        Path savePath = Paths.get(saveName);
        //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)

        try{
            file.transferTo(savePath);
            //uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
        } catch (IOException e) {
            e.printStackTrace();
            //printStackTrace()를 호출하면 로그에 Stack trace 출력됩니다.
        }

        memberSaveReqDto.setPhoto(String.valueOf(savePath));

        MemberRespDto memberRespDto = adminMemberService.joinMember(memberSaveReqDto);

        return "redirect:/admin/memberList";
    }

    // 폴더 생성 메소드
    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        //LocalDate를 문자열로 포멧

        String folderPath = str.replace("\\", File.separator);
        //만약 Data 밑에 exam.jpg라는 파일을 원한다고 할때,
        //윈도우는 "Data\\"eaxm.jpg", 리눅스는 "Data/exam.jpg"라고 씁니다.
        //그러나 자바에서는 "Data" +File.separator + "exam.jpg" 라고 쓰면 됩니다.

        //make folder ==================
        File uploadPathFolder = new File(uploadPath, folderPath);
        //File newFile= new File(dir,"파일명");
        //->부모 디렉토리를 파라미터로 인스턴스 생성

        if(!uploadPathFolder.exists()){
            uploadPathFolder.mkdirs();
            //만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
            //mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
            //mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
        }
        return folderPath;
    }


    // 회원 정보 상세보기
    @GetMapping("/memberDetail")
    public String memberDetail(@RequestParam Long mno, Model model){
        Member member = adminMemberRepository.findById(mno).orElse(null);
        if(member == null){
            return "redirect:/admin/memberList";
        }else{
            Path path = Paths.get(member.getPhoto());
            model.addAttribute("fileName", path.getFileName());
            model.addAttribute("member", member);

            return "admin/memberDetail";
        }

    }

    // 회원 삭제 memberDelete (FALSE -> TRUE 변경)
    @GetMapping("/memberDelete")
    public String memberDelete(@RequestParam Long mno){

        adminMemberService.deleteMember(mno);

        return "redirect:/admin/memberList";
    }


}
