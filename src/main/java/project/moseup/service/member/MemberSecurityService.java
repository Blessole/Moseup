package project.moseup.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.Member;
import project.moseup.repository.member.MemberInterfaceRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSecurityService implements UserDetailsService {

    private final MemberInterfaceRepository memberInterfaceRepository;

    /** Spring Security 필수 메소드 **/
    // @param email, @return UserDetails, @throws UsernameNotFoundException (유저가 없을 때 예외 발생)
    @Override
    // 기본 반환 타입은 UserDetails이지만,
    // UserDetails를 상속받은 Member로 반환 타입 지정(자동 다운캐스팅)
    public Member loadUserByUsername(String email) throws UsernameNotFoundException {
        return  memberInterfaceRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException((email)));
    }
//
//    @Override
//    public Member loadUserByUsername(String email) throws UsernameNotFoundException {
//        // 로그인 하기 위해 가입된 user정보 조회하는 메소드
////        Member member = memberInterfaceRepository.findByEmail(email).orElseThrow(() ->
////                new UsernameNotFoundException("아이디가 없습니다 : " + email));
//
////        Optional<Member> memberWrapper = this.memberInterfaceRepository.findByEmail(email);
////        if (memberWrapper.isEmpty()) {
////            throw new UsernameNotFoundException("아이디가 없습니다 : " + email);
////        }
//
////        Member member = memberWrapper.get();
//
////        Member customUserDetails = new CustomUserDetails();
////        if ( member != null){
//////            customUserDetails.
////        }
////
////        List<GrantedAuthority> authorities = new ArrayList<>();
////        System.out.println("member Security Service 지나감");
////        if (email.equals("admin@admin.com")) {
////            System.out.println("어드민 가입 중");
////            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
////        } else {
////            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
////        }
////        return new User(member.getEmail(), member.getPassword(), authorities);
//        return  memberInterfaceRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException((email)));
//    }

}