//package project.moseup.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import project.moseup.domain.Member;
//import project.moseup.exception.NoLoginException;
//import project.moseup.service.member.MemberService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.security.Principal;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class MyInterceptor extends HandlerInterceptorAdapter {
//
//
//    private final NavBar navBar;
//    // controller로 보내기 전에 처리하는 인터셉터
//    // 반환이 false라면 controller로 요청을 안함
//    // 매개변수 Object는 핸들러 정보를 의미한다. ( RequestMapping , DefaultServletHandler )
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("MyInterceptor - preHandle");
//
//        navBar.solTest();
//
////        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        System.out.println("user : " + user);
////        System.out.println("o : " + user.getUsername());
//
////        Map<String, Object> map = null;
////        if (user.equals("anonymousUser")){
////            System.out.println("여기 지나간다 1");
//////            throw new NoLoginException();
////        } else {
////            System.out.println("여기 지나간다 2");
////            System.out.println("제발 : " + user.getUsername());
////            map = memberService.getPhotoAndNicknameForNavBar(user.getUsername());
////            if (map == null) {
////                System.out.println("map이 null 나와버림");
////            } else {
////                System.out.println("member : " + map);
////                Model model = null;
////                model.addAttribute("navMember", map);
////            }
////        }
//        return super.preHandle(request, response, handler);
//    }
//
//    //            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> map = new HashMap<>();
////            map.put("navMap", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
////            Map<String, Object> map = objectMapper.convertValue(member, Map.class);
//
//
//}
//
