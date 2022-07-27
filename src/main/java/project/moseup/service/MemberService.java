package project.moseup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import project.moseup.controller.JoinForm;
import project.moseup.domain.Member;
import project.moseup.domain.MemberGender;
import project.moseup.domain.Role;
import project.moseup.repository.MemberInterfaceRepository;
import project.moseup.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private MemberInterfaceRepository memberInterfaceRepository;
	private BCryptPasswordEncoder passwordEncoder;
	/** 회원가입 **/
//	@Transactional	// 값을 넣어야하는 곳에는 읽기만 하면 안되니 따로 @Transactional를 사용
//	public void join(Member member) {
//		validateDuplicateMember(member);
//		memberRepository.save(member);
//	}

	@Transactional
	public void join(JoinForm joinForm) {
		// password 암호화
		joinForm.setPassword(passwordEncoder.encode(joinForm.getPassword()));

		//중복 확인
//		validateDuplicateMember(joinForm);

		// DB 저장
		memberRepository.save(joinForm.toEntity());
	}

	/** 중복 회원 검증 **/
	private void validateDuplicateMember(JoinForm joinForm) {
		List<Member> findMembers = memberRepository.findByEmail(joinForm.getEmail());
		if (!findMembers.isEmpty()){
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/** 로그인 **/
	public boolean login(Member member) {
		System.out.println("member email 2 :"+member.getEmail());
		System.out.println("member pw 2 : "+member.getPassword());
		Member findMember = memberRepository.findOneEmail(member.getEmail());
		System.out.println("findMember id:"+findMember.getEmail());
		System.out.println("findMember pw:"+ findMember.getPassword());
		System.out.println("과연? : " + findMember.getPassword().equals(member.getPassword()));
		System.out.println("과연2? : " + passwordEncoder.matches(member.getPassword(), findMember.getPassword()));
		if (findMember == null) {
			System.out.println("여기지나감1");
			return false;
		}
		if (passwordEncoder.matches(member.getPassword(), findMember.getPassword())){
			System.out.println("여기지나감2");
			return false;
		}
		System.out.println("여기지나감3");
		return true;
	}

	/** 회원 전체 조회 **/
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	/** 회원 단건 조회 **/
	public Member findOne(Long memberId) {
		return memberRepository.findOneMno(memberId);
	}

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		// 로그인 하기 위해 가입된 user정보 조회하는 메소드
		Optional<Member> memberWrapper = memberInterfaceRepository.findByEmail(email);
		Member member = memberWrapper.get();

		List<GrantedAuthority> authorities = new ArrayList<>();

		if ("admin@admin.com".equals(email)){
			authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
		}

		return  new User(member.getEmail(), member.getPassword(), authorities);
	}
}
