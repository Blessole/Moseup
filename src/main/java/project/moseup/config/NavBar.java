//package project.moseup.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import project.moseup.domain.Member;
//import project.moseup.exception.NoLoginException;
//import project.moseup.repository.member.MemberInterfaceRepository;
//import project.moseup.service.member.MemberService;
//
//import java.security.Principal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//public class NavBar {
//
//    private final MemberInterfaceRepository memberInterfaceRepository;
//
//
//    @ModelAttribute
//    public void solTest() {
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("user : " + user);
//        System.out.println("o : " + user.getUsername());
//        Model model = null;
//        Map<String, Object> map = null;
//        if (user.equals("anonymousUser")){
//            System.out.println("여기 지나간다 1");
////            throw new NoLoginException();
//        } else {
//            System.out.println("여기 지나간다 2");
//            System.out.println("제발 : " + user.getUsername());
//            map = this.getPhotoAndNicknameForNavBar(user.getUsername());
//            if (map == null) {
//                System.out.println("map이 null 나와버림");
//            } else {
//                System.out.println("member : " + map);
//
//                model.addAttribute("navMember", map);
//            }
//        }
//    }
//
//    public Map<String, Object> getPhotoAndNicknameForNavBar(String email) {
//        Map<String, Object> map = new HashMap<>();
//        Member member = this.getMember(email);
//        map.put("member", member);
//        System.out.println("service member : " + member);
//
//        String realPhoto = "";
//        if (member.getPhoto()==null || member.getPhoto().equals("")){
//            realPhoto = "/images/profile.png";
//            map.put("realPhoto", realPhoto);
//        } else {
//            // 사진 경로 local에서 project용으로 변경
//            String photo = member.getPhoto();
//            int index = photo.indexOf("images");
//            realPhoto = photo.substring(index - 1);
//            map.put("realPhoto", realPhoto);
//        }
//        System.out.println("service realPhoto : " + realPhoto);
//        System.out.println("service map : " + map);
//
//        return map;
//    }
//
//    /** 회원 정보 조회 **/
//    public Member getMember(String email) {
//        log.info("왜이러는겨1");
//        Optional<Member> member = memberInterfaceRepository.findByEmail(email);
//        if (member.isPresent()) {
//            log.info("왜이러는겨1" + member.get());
//            return member.get();
//        } else {
//            throw new UsernameNotFoundException("member not found");
//        }
//    }
//
////    @ModelAttribute
////    public void loginMember(Principal principal, Model model){
////        if (principal == null){
////            throw new NoLoginException();
////        } else {
////            Map<String, Object> memberMap = memberService.getPhotoAndNickname(principal);
////            model.addAttribute("navMember", memberMap);
////        }
////    }
//}
