package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.domain.*;
import project.moseup.dto.*;
import project.moseup.dto.searchDto.MemberDateSearchDto;
import project.moseup.exception.MemberNotFoundException;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.repository.myPage.AskBoardInterfaceRepository;
import project.moseup.repository.myPage.BankbookInterfaceRepository;
import project.moseup.repository.teampage.CheckBoardPageRepository;
import project.moseup.repository.teampage.TeamAskBoardPageRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;
    private final BankbookInterfaceRepository bankbookInterfaceRepository;
    private final CheckBoardPageRepository checkBoardPageRepository;
    private final PasswordEncoder passwordEncoder;
    private final AskBoardInterfaceRepository askBoardInterfaceRepository;

    private final TeamAskBoardPageRepository teamAskBoardPageRepository;

    // 회원 등록
    @Transactional(rollbackFor = RuntimeException.class) //런타임 예외가 발생하면 롤백
    public MemberRespDto joinMember(MemberSaveReqDto memberSaveReqDto) {
        Member memberPS = adminMemberRepository.save(memberSaveReqDto.toEntity());
        memberPS.encodePassword(passwordEncoder);

        return new MemberRespDto().toDto(memberPS);
        // 컨트롤러는 DTO 데이터를 가지고 있게하고 클라이언트한테 DTO 데이터를 넘겨줌 = Entity 데이터를 그대로 주면 연관된(조인) 다른 데이터 즉, 클라이언트 입장에서 불필요한 데이터까지 날아감을 방지
        // 간략한 흐름도 ↓
        // [데이터 전송]     [DTO 받음]  [DTO -> Entity] [Entity 받음]  [영속화]  [DB 저장]
        // 클라이언트 ----> 컨트롤러 ----> 서비스 ---->    레퍼지토리 ---->  PS ----> DB
        // [DTO 응답]  <-  [DTO 받음]  [Entity -> DTO] <---- 다시 응답 <-----------↑
    }

    // 회원 삭제(업데이트)
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteMember(Long id) {
        Member member = adminMemberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
        if(member != null){
            member.deleteUpdate(DeleteStatus.TRUE);
            adminMemberRepository.save(member);
        }
    }

    // 회원 복구(업데이트)
    @Transactional(rollbackFor = RuntimeException.class)
    public void memberRecover(Long id) {
        Member member = adminMemberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
        member.deleteUpdate(DeleteStatus.FALSE);
        adminMemberRepository.save(member);
    }

    //테스트용
    public List<MemberRespDto> memberFindAll(){
        return adminMemberRepository.findAll().stream()
                .map(memberPS -> new MemberRespDto().toDto(memberPS))
                .collect(Collectors.toList());
    }

    //테스트용
    public MemberRespDto memberFindBy(Long id){
        Member member = adminMemberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
        return member.toDto();
    }

    public MemberRespDto memberUpdate(Long id, MemberSaveReqDto dto){
        Member member = adminMemberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
        member.infoUpdate(dto.toUpdate());
        return member.toDto();
    }

    public Page<Member> members(MemberDateSearchDto searchDto, Pageable pageable){
        Page<Member> members;

        switch (searchDto.getOrderBy()){
            case "deleteTrue": members = adminMemberRepository.findByMemberDelete(DeleteStatus.TRUE, pageable);
                break;
            case "admin": members = adminMemberRepository.findByRole(Role.ADMIN, pageable);
                break;
            default: members = adminMemberRepository.
                    findByEmailContainingOrNameContainingOrNicknameContaining
                            (searchDto.getKeyword(), searchDto.getKeyword(), searchDto.getKeyword(), pageable);
                break;
        }
        if(searchDto.getStartDate() != null && searchDto.getEndDate() != null){
            LocalDate startDate = null;
            LocalDate endDate = null;
            try {
                startDate = LocalDate.parse(searchDto.getStartDate());
                endDate = LocalDate.parse(searchDto.getEndDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
            members = adminMemberRepository.findByMemberDateBetween(startDate, endDate, pageable);
        }
        return members;
    }


    public Map<String, Object> getMemberMap(Long id) {
        Map<String, Object> map = new HashMap<>();
        Member member = adminMemberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));

        String photo = member.getPhoto();
        int index = photo.indexOf("images");
        String realPhoto = photo.substring(index - 1);

        map.put("member", member);
        map.put("realPath", realPhoto);
        return map;
    }


    public Map<String, Object> getBankbookMap(Long id) {
        Map<String, Object> map = new HashMap<>();
        BankbookRespDto bankbookRespDto = null;
        int bankbookTotal = 0;
        Member member = adminMemberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(id));

        if(!member.getBankbooks().isEmpty()){
            int bankbookSize = member.getBankbooks().size();
            bankbookTotal = member.getBankbooks().get(bankbookSize - 1).getBankbookTotal();
        }

        Bankbook bankbookPS = bankbookInterfaceRepository.findByMember(member);

        if(bankbookPS != null){
            bankbookRespDto = new BankbookRespDto().toDto(bankbookPS);
        }

        map.put("bankbookTotal", bankbookTotal);
        map.put("bankbook", bankbookRespDto);

        return map;
    }

    public List<CheckBoardRespDto> getCheckBoards(Long id) {
        Optional<Member> memberOP = adminMemberRepository.findById(id);
        if(memberOP.isPresent()){
            Member memberPS = memberOP.get();
            List<CheckBoard> checkBoards = checkBoardPageRepository.findByMemberOrderByCnoDesc(memberPS);

            return checkBoards.stream().map(checkBoard -> new CheckBoardRespDto().toDto(checkBoard)).collect(Collectors.toList());
        }else{
            throw new MemberNotFoundException(id);
        }
    }

    public Map<String, Object> getAskBoard(Long mno) {
        Map<String, Object> isResult = new HashMap<>();
        Optional<Member> memberOP = adminMemberRepository.findById(mno);

        if(memberOP.isPresent()){
            List<AskBoardRespDto> askBoardRespDtoList = askBoardInterfaceRepository
                    .findByMemberOrderByAnoDesc(memberOP.get())
                    .stream()
                    .map(askBoard -> new AskBoardRespDto().toDto(askBoard))
                    .collect(Collectors.toList());

        isResult.put("askBoard", askBoardRespDtoList);
        isResult.put("member", memberOP.get());

        return isResult;
        }else{
            throw new NullPointerException("회원 정보가 없습니다 ID = " + mno);
        }
    }

    public Map<String, Object> getTeamAskBoard(Long mno) {
        Map<String, Object> isResult = new HashMap<>();
        Optional<Member> memberOP = adminMemberRepository.findById(mno);

        if(memberOP.isPresent()){
            List<TeamAskBoardRespDto> askBoardRespDtoList = teamAskBoardPageRepository
                    .findByMemberOrderByTanoDesc(memberOP.get())
                    .stream()
                    .map(teamAskBoard -> new TeamAskBoardRespDto().toDto(teamAskBoard))
                    .collect(Collectors.toList());

            isResult.put("teamAskBoards", askBoardRespDtoList);
            isResult.put("member", memberOP.get());

            return isResult;
        }else{
            throw new NullPointerException("회원 정보가 없습니다 ID = " + mno);
        }

    }
}
