package project.moseup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	
	/** 회원가입 **/
	@Transactional	// 값을 넣어야하는 곳에는 읽기만 하면 안되니 따로 @Transactional를 사용
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getMno();
	}

	/** 중복 회원 검증 **/
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByMno(member.getMno());
		if (!findMembers.isEmpty()){
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/** 회원 전체 조회 **/
	public List<Member> findMembers(){
		return memberRepository.findAll();
	}

	/** 회원 단건 조회 **/
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
