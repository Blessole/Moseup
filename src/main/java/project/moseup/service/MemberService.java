package project.moseup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	
	@Transactional
	public Long join(Member member) {
		memberRepository.save(member);
		return member.getMno();
	}
	
	// 회원 단건 조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
