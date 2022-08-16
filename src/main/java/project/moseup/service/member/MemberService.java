package project.moseup.service.member;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.dto.KakaoLoginForm;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.member.MemberInterfaceRepository;
import project.moseup.repository.member.MemberRepository;

import javax.validation.constraints.NotEmpty;
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
	public void join(MemberSaveReqDto joinForm) {
		System.out.println("password : " + joinForm.getPassword());

		Member member = joinForm.toEntity();

		// password 암호화
		member.encodePassword(passwordEncoder);

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
	public Long update(MemberSaveReqDto memberDto, Long mno) {
		Member member = memberInterfaceRepository.findById(mno).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
		member.infoUpdate(memberDto);
//		memberInterfaceRepository.update(member);
		return mno;
	}

	/** 카카오 로그인 **/
	@Transactional
	public Member loginWithKakao(Member member){
		Member savedMember = memberRepository.findOneEmail(member.getEmail());
		if(savedMember == null){
			memberRepository.save(member);
		}
		return savedMember;
	}

	/** 회원 탈퇴 **/
	@Transactional
	public void delete(String loginId) {
		Member member = memberInterfaceRepository.findByEmail(loginId).orElseThrow(()-> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));
		member.deleteUpdate(DeleteStatus.TRUE);
		memberRepository.save(member);
	}

	/** 회원정보 확인 전 비밀번호 확인**/
	public Boolean checkPassword(@NotEmpty String password, String loginId) {
		Member member = memberRepository.findOneEmail(loginId);
		return passwordEncoder.matches(password, member.getPassword());
	}
}
