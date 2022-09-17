package project.moseup.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
@WebListener
public class SessionListener extends HttpSessionEventPublisher {

    // 세션처리
    private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    //중복로그인 지우기
    public static synchronized String getSessionIdCheck(String type, String compareId){
        String result = "";
        for( String key : sessions.keySet() ){
            HttpSession hs = sessions.get(key);
            if(hs != null &&  hs.getAttribute(type) != null && hs.getAttribute(type).toString().equals(compareId) ){
                result = key;
            }
        }
        removeSessionForDoubleLogin(result);
        return result;
    }

    private static void removeSessionForDoubleLogin(String userId){
        log.info("remove userId : " + userId);
        if(userId != null && userId.length() > 0){
            sessions.get(userId).invalidate();
            sessions.remove(userId);
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //세션 생성시
        log.info("sessionCreated = " + se);
        sessions.put(se.getSession().getId(), se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //세션 삭제시
        log.info("sessionDestroyed = " + se);
        if(sessions.get(se.getSession().getId()) != null){
            sessions.get(se.getSession().getId()).invalidate();
            sessions.remove(se.getSession().getId());
        }
    }
}
