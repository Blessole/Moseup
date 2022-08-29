package project.moseup.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.dto.Mail;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    private static final String title = "[모습] 임시 비밀번호 안내";
    private static final String message = "안녕하세요. 모두의 습관을 만드는 스터디 게더링 플랫폼 '모습'입니다."
            +"\n"+"회원님의 임시 비밀번호는 아래와 같습니다."
            +"\n"+"로그인 후 반드시 비밀번호를 변경해주세요." +"\n";

    // 메일 경로
    @Value("${spring.mail.username}")
    private String from;

    // 이메일 생성
    public Mail createMail(String tmpPw, String email){
        Mail mail = Mail.builder()
                .toAddress(email)
                .title(title)
                .message(message + tmpPw)
                .fromAddress(from)
                .build();

        log.info("메일 생성 완료");
        return mail;
    }
    
    // 이메일 전송
    public void sendMail(Mail mail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getToAddress());
        mailMessage.setSubject(mail.getTitle());
        mailMessage.setText(mail.getMessage());
        mailMessage.setFrom(mail.getFromAddress());
        mailMessage.setReplyTo(mail.getFromAddress());
        
        mailSender.send(mailMessage);
        log.info("메일 전송 완료");
    }
}
