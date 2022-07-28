package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;

//    // 이메일 중복 체크
//    @Transactional
//    public boolean memberEmailCheck(String email){
//        return adminMemberRepository.existsByEmail(email);
//
//    }

    @Transactional
    public Member joinMember(MemberSaveReqDto memberSaveReqDto) {
        Member member = memberSaveReqDto.toEntity();
        return adminMemberRepository.save(member);
    }

    public void deleteMember(Long mno) {
        adminMemberRepository.findById(mno).ifPresent(memberPS -> memberPS.update(DeleteStatus.TRUE));
    }

}
