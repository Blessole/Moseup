package project.moseup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.AskBoard;
import project.moseup.domain.AskBoardReply;
import project.moseup.domain.Member;
import project.moseup.dto.AskBoardReplySaveReqDto;
import project.moseup.dto.AskBoardSaveReqDto;
import project.moseup.exception.NoLoginException;
import project.moseup.service.member.MemberService;
import project.moseup.service.myPage.AskBoardReplyService;
import project.moseup.service.myPage.AskBoardService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("askBoard")
public class AskBoardController {

    private final MemberService memberService;
    private final AskBoardService askBoardService;
    private final AskBoardReplyService askBoardReplyService;

    // 파일 업로드 경로
    @Value("${moseup.upload.path}") //application.properties의 변수
    private String uploadPath;

    @GetMapping("/askBoardList")
    public String askBoardList(Model model, Principal principal, @RequestParam(value="page", defaultValue = "0") int page){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);

        model.addAttribute("askBoardList", askBoardService.findAskBoardsPaging((Member) map.get("member"), page));
        model.addAttribute("map", map);
        model.addAttribute("maxPage", 5);
        return "myPage/askBoardList";
    }

    @GetMapping("/askBoardForm")
    public String askBoardForm(Model model, Principal principal){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);

        model.addAttribute("askBoardForm", new AskBoardSaveReqDto());
        model.addAttribute("map", map);
        return "myPage/askBoardForm";
    }

    @PostMapping("/ask")
    public String askBoardAction(@Valid @ModelAttribute("askBoardForm") AskBoardSaveReqDto askBoardForm, BindingResult bindingResult,
                                 @RequestPart(required = false) MultipartFile file, Principal principal, Model model) throws IOException{
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        model.addAttribute("map", map);

        System.out.println("error:"+ bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            for(ObjectError e : list){
                System.out.println(e.getDefaultMessage());
            }
            model.addAttribute("askBoardForm", new AskBoardSaveReqDto());
            return "redirect:/askBoard/askBoardForm";
        }

        // 작성자 정보 SET
        askBoardForm.setMember((Member) map.get("member"));

        // 파일 업로드
        if (file.isEmpty()){
            askBoardForm.setAskPhoto(null);
        } else if (!file.isEmpty()){
            if (file.getContentType().startsWith("image")== false){
                System.out.println("ABC - 이미지 파일만 올리셈");
                System.out.println("content type : "+ file.getContentType());
                return "redirect:/askBoard/askBoardForm";
            }

            // askBoard 용 폴더 생성
            String folderPath = "askBoard";
            String saveName = memberService.makeFolderAndFileName(file, folderPath);
            // form에 저장
            askBoardForm.setAskPhoto(saveName);
        }

        // 내용에 엔터 태그 적용되도록 변경하여 저장
        askBoardForm.setAskContent(askBoardForm.getAskContent().replace("\r\n", "<br>"));

        try{
            askBoardService.save(askBoardForm);
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("askBoardSaveFailed", "아쉽게도 등록 실패다!");
            return "myPage/askBoardForm";
        } catch (Exception e){
            e.printStackTrace();
            bindingResult.reject("askBoardSaveFailed", e.getMessage());
            return "myPage/askBoardForm";

        }
        return "redirect:/askBoard/askBoardList";
    }

    @GetMapping("/askBoardDetail")
    public String askBoardDetail(@RequestParam(name = "ano") Long ano, Model model, Principal principal, @RequestParam(value="page",  defaultValue = "0") int page){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        AskBoard askBoard = this.askBoardService.findOne(ano);

        String realPhoto = "";
        if (askBoard.getAskPhoto() == null || askBoard.getAskPhoto().equals("")){
            realPhoto = "";
        } else {
            String photo = askBoard.getAskPhoto();
            int index = photo.indexOf("images");
            realPhoto = photo.substring(index - 1);
        } model.addAttribute("photoPath", realPhoto);

        if ( askBoardReplyService.findAll(askBoard, page) != null ) {
            Page<AskBoardReply> askBoardReplies = this.askBoardReplyService.findAll(askBoard, page);
            model.addAttribute("askBoardReplies", askBoardReplies);
        }

        // 내용에 엔터 태그 적용되도록 변경하여 저장
        String askContent = askBoard.getAskContent().replace("<br>", "<br/>");
        model.addAttribute("askContent1", askContent); // DTO 이용했으면 없었을 코드임 (Setter!!!!!!!!!!!!!)
        model.addAttribute("askBoard", askBoard);
        model.addAttribute("map", map);
        model.addAttribute("askBoardReplyDto", new AskBoardReplySaveReqDto());

        // clean code 생각해보기 - detail & update 코드 동일!
        return "/myPage/askBoardDetail";
    }

    /** 글 수정 **/
    @GetMapping("/askBoardUpdateForm")
    public String askBoardUpdateForm(@RequestParam("ano") Long ano, Model model, Principal principal){
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        AskBoardSaveReqDto askBoardDto = this.askBoardService.getPost(ano);

        // 내용에 엔터 태그 적용되도록 변경하여 set
        askBoardDto.setAskContent(askBoardDto.getAskContent().replace("<br>", "\r\n"));

        model.addAttribute("ano", ano);
        model.addAttribute("askBoardDto", askBoardDto);
        model.addAttribute("map", map);
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

        // 내용에 엔터 태그 적용되도록 변경하여 저장
        askBoardDto.setAskContent(askBoardDto.getAskContent().replace("\r\n", "<br>"));

        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        model.addAttribute("map", map);

        askBoardService.update(askBoardDto, ano);

        return "redirect:/askBoard/askBoardDetail?ano="+ano;
    }

    /** 글 삭제 **/
    @GetMapping("/askBoardDelete")
    public String askBoardDelete(@RequestParam Long ano, Model model, Principal principal) {
        Map<String, Object> map = memberService.getPhotoAndNickname(principal);
        model.addAttribute("map", map);

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

//    @PostMapping("/image")
//    public void handlerFileUpload(@RequestParam("file")MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
//        // askBoard 용 폴더 생성
//        String folderPath = "askBoard";
//        File uploadPathFolder = new File(uploadPath, folderPath);
//        if(!uploadPathFolder.exists())  {
//            try{
//                uploadPathFolder.mkdirs(); //폴더생성
//            } catch (Exception e ){
//                e.getStackTrace(); //에러발생
//            }
//        }
//
//        // 사진 업로드!
//        try{
//            PrintWriter out = response.getWriter();
//            UUID uuid = UUID.randomUUID();
//
//            // 업로드 할 파일 이름
//            String org_filename = file.getOriginalFilename();
//            String str_filename = uuid.toString() + "_" + org_filename;
//
//            log.info("org_filename ====" + org_filename);
//            log.info("str_filename ====" + str_filename);
//
//            String filePath = uploadPath +  File.separator + folderPath +  File.separator  + str_filename;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    @PostMapping("/image")
//    public ResponseEntity<?> summerImage(@RequestParam("file") MultipartFile img, HttpServletRequest request) throws IOException{
//        System.out.println("여기 지나가긴 하니..?");
//        String path = request.getServletContext().getRealPath("resources/static/images");
//        Random random = new Random();
//
//        long currentTime = System.currentTimeMillis();
//        int randomValue = random.nextInt(100);
//        String fileName = Long.toString(currentTime)+"_"+randomValue+"_a_"+img.getOriginalFilename();
//
//        File file = new File(path, fileName);
//        img.transferTo(file);
//        return ResponseEntity.ok().body("askBoard/image/"+fileName);
//    }

}
