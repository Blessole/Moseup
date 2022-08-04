package project.moseup.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
        Optional<Member> memberWrapper = this.memberInterfaceRepository.findByEmail(email);
        if (memberWrapper.isEmpty()) {
            throw new UsernameNotFoundException("아이디가 없습니다 : " + email);
        }

        Member member = memberWrapper.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin@admin.com".equals(email)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return new User(member.getEmail(), member.getPassword(), authorities);


    }
}