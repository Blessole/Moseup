package project.moseup.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import project.moseup.service.member.MemberService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

//    private String email;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException{
        // 방문자 카운트
//        String username = request.getParameter(email);
////        request.setAttribute(email, email);  사용 안해도 될 듯?
//        loginSuccessCount(username);
//        log.info("방문자 카운트 증가");

//        // 로그인 실패 후 성공 시 남아있는 에러 세션 제거
//        clearSession(request);
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        /* prevPage가 존재하는 경우 : 사용자가 직접 로그인 요청한 것
        * 기존 session의 prevPage attribute 제거 */
        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if(prevPage != null){
            request.getSession().removeAttribute("prevPage");
        }

        // 기본 URI
        String uri = "/";

        /* savedRequest 존재하는 경우 : 인증 권한이 없는 페이지 접근
        *  Security filter 가 인터셉트하여 savedRequest에 세션 저장 */
        if (savedRequest != null){
            log.info("prevPage : " +prevPage + ", savedRequest : " +savedRequest);
            uri = savedRequest.getRedirectUrl();
        } else if (prevPage != null && !prevPage.equals("")){
            log.info("prevPage 1 : " + prevPage);
            // 회원가입 - 로그인으로 넘어온 경우 "/"로 redirect
            if(prevPage.contains("/join")){
                uri="/";
            } else {
                uri = prevPage;
            }
        }
        log.info("uri" + uri);
        redirectStrategy.sendRedirect(request, response, uri);
    }

    // 로그인 실패 후 성공 시 남아있는 에러 세션 제거
    protected void clearSession(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

//    protected void loginSuccessCount(String username){
//        memberService.countSuccess(username);
//
//    }

//    public String getEmail(){
//        return email;
//    }
//
//    public void setEmail(String email){
//        this.email = email;
//    }
}
