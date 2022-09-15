//package project.moseup.config;
//
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import project.moseup.domain.DeleteStatus;
//
//import java.io.Serializable;
//import java.util.Collection;
//
//@Getter
//public class CustomUserDetails implements UserDetails, Serializable {
//    private String mno;
//    private String email;
//    private String password;
//    private String nickname;
//    private DeleteStatus memberDelete;
//    private Collection<GrantedAuthority> authorities;
//
//    /** 해당 유저의 권한 목록 **/
//    @Override
//    public Collection<? extends  GrantedAuthority> getAuthorities(){
//        return authorities;
//    }
//
//    /** PK 값 **/
//    @Override
//    public String getUsername() {
//        return mno;
//    }
//
//    /** 계정 만료 여부 **/
//    // true : 만료 X, false : 만료
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    /** 계정 잠김 여부 **/
//    // true : 잠기지 X, false : 잠김
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    /** 비밀번호 만료 여부 **/
//    // true : 만료 X, false : 만료
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    /** 사용자 활성 여부 **/
//    // true : 활성 O, false : 비활성화
//    @Override
//    public boolean isEnabled() {
//        boolean locked = false;
//        if (memberDelete == DeleteStatus.FALSE){
//            locked = true;
//        } else if (memberDelete == DeleteStatus.TRUE){
//            locked = false;
//        }
//        return locked;
//    }
//}
