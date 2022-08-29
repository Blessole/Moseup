//package project.moseup.config;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.Model;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import project.moseup.domain.Member;
//import project.moseup.service.member.MemberService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.security.Principal;
//
//@Component
//public class MyInterceptor extends HandlerInterceptorAdapter {
//
//    private MemberService memberService;
//    // controller로 보내기 전에 처리하는 인터셉터
//    // 반환이 false라면 controller로 요청을 안함
//    // 매개변수 Object는 핸들러 정보를 의미한다. ( RequestMapping , DefaultServletHandler )
//    @Override
//    public boolean preHandle(
//            HttpServletRequest request, HttpServletResponse response,
//            Object handler, Principal principal, Model model) throws Exception {
//        System.out.println("MyInterceptor - preHandle");
//
//        this.getNav(principal, model);
//        return super.preHandle(request, response, handler);
//    }
//
//    @Override
//    @PreAuthorize("isAuthenticated()")
//    public void getNav(Principal principal, Model model){
//        memberService.getPhotoAndNickname(principal, model);
//        }
//    }
//
