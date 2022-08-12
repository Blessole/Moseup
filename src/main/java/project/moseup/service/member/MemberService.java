package project.moseup.service.member;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import project.moseup.dto.JoinFormDto;
import project.moseup.domain.Member;
import project.moseup.dto.MyInfoDto;
import project.moseup.repository.member.MemberInterfaceRepository;
import project.moseup.repository.member.MemberRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberInterfaceRepository memberInterfaceRepository;
	private final PasswordEncoder passwordEncoder;

	/** principal 조회 **/
	public Member getPrincipal(Principal principal) {
		Member member = this.getMember(principal.getName());
		return member;
	}

	/** 회원 정보 조회 **/
	public Member getMember(String email) {
		Optional<Member> member = this.memberInterfaceRepository.findByEmail(email);
		System.out.println("MemberService member : " + member);
		if (member.isPresent()){
			return member.get();
		} else {
			throw new UsernameNotFoundException("member not found");
		}
	}

	/** 회원가입 **/
	@Transactional // 값을 넣어야하는 곳에는 읽기만 하면 안되니 따로 @Transactional를 사용
	public void join(JoinFormDto joinForm) {
		System.out.println("password : " + joinForm.getPassword1());

		Member member = joinForm.toEntity();

		// password 암호화
		member.encodePassword(passwordEncoder);

		//중복 확인
//		if (memberInterfaceRepository.findByEmail(joinForm.getEmail()).isPresent()){
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
//		}
//		validateDuplicateMember(joinForm.getEmail());

		// DB 저장
		memberRepository.save(member);
	}

	/** 회원 전체 조회 **/
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	/** 회원 단건 조회 **/
	public Member findOne(Long memberId) {
		return memberRepository.findOneMno(memberId);
	}

	/** 회원 정보 수정 **/
	@Transactional
	public Long update(MyInfoDto myInfoDto, Long mno) {
		Member member = memberInterfaceRepository.findById(mno).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
		member.infoUpdate(myInfoDto);
		return mno;
	}
}
