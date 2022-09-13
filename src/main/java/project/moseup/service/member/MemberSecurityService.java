package project.moseup.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.moseup.config.CustomUserDetails;
import project.moseup.domain.Member;
import project.moseup.domain.Role;
import project.moseup.repository.member.MemberInterfaceRepository;
import project.moseup.repository.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberInterfaceRepository memberInterfaceRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 로그인 하기 위해 가입된 user정보 조회하는 메소드
        Member member = memberInterfaceRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("아이디가 없습니다 : " + email));

//        Optional<Member> memberWrapper = this.memberInterfaceRepository.findByEmail(email);
//        if (memberWrapper.isEmpty()) {
//            throw new UsernameNotFoundException("아이디가 없습니다 : " + email);
//        }

//        Member member = memberWrapper.get();

        CustomUserDetails customUserDetails = new CustomUserDetails();
        if ( member != null){
//            customUserDetails.
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("member Security Service 지나감");
        if (email.equals("admin@admin.com")) {
            System.out.println("어드민 가입 중");
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return new User(member.getEmail(), member.getPassword(), authorities);
    }

}