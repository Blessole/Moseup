package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.DeleteStatus;
import project.moseup.domain.Member;
import project.moseup.dto.MemberRespDto;
import project.moseup.dto.MemberSaveReqDto;
import project.moseup.repository.admin.AdminMemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;

    // 회원 등록
    @Transactional(rollbackFor = RuntimeException.class) //런타임 예외가 발생하면 롤백
    public MemberRespDto joinMember(MemberSaveReqDto memberSaveReqDto) {
        Member memberPS = adminMemberRepository.save(memberSaveReqDto.toEntity());
        return new MemberRespDto().toDto(memberPS);
        // 컨트롤러는 DTO 데이터를 가지고 있게하고 클라이언트한테 DTO 데이터를 넘겨줌 = Entity 데이터를 그대로 주면 연관된(조인) 다른 데이터 즉, 클라이언트 입장에서 불필요한 데이터까지 날아감을 방지
        // 간략한 흐름도 ↓
        // [데이터 전송]     [DTO 받음]  [DTO -> Entity] [Entity 받음]  [영속화]  [DB 저장]
        // 클라이언트 ----> 컨트롤러 ----> 서비스 ---->    레퍼지토리 ---->  PS ----> DB
        // [DTO 응답]  <-  [DTO 받음]  [Entity -> DTO] <---- 다시 응답 <-----------↑
    }

    // 회원 삭제(업데이트)
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteMember(Long mno) {
        Member member = adminMemberRepository.findById(mno).orElse(null);

        member.deleteUpdate(DeleteStatus.TRUE);

        adminMemberRepository.save(member);
    }


}
