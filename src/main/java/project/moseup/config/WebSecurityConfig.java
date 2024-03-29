package project.moseup.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import project.moseup.service.member.MemberSecurityService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberSecurityService memberSecurityService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/teams/teamPage", "/members/**", "/search/**").permitAll()
                    .antMatchers("/myPage/**", "/teams/**").authenticated()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()   //위에 적은 패턴 외에는 모두 로그인인증하도록 만듦
                .and()
                    .formLogin()
                    .loginPage("/members/login")
//                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .successHandler(authenticationSuccessHandler) // 로그인 성공 시!
                    .permitAll()        // 로그인 하지 않은 사용자도 로그인 페이지에 접근할 수 있도록
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                    .logoutSuccessUrl("/members/login")
                    .invalidateHttpSession(true)   //세션 날리기
//                .and()
//                .exceptionHandling().accessDeniedPage("/error");
                .and()
                    .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true) // 중복 세션 체크 true = 새로운 사용자 인증실패 false = 이전 사용자 세션만료
                    .expiredUrl("/members/login"); //세션 만료되었을 경우 리다이렉트 할 페이지
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // 여기서부터 다시 만든거
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberSecurityService)
                .passwordEncoder(passwordEncoder());
    }

//    public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//        @Bean
//        @Override
//        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        }
//    }
}
