package project.moseup.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.moseup.dto.AskBoardRespDto;
import project.moseup.dto.FreeBoardRespDto;
import project.moseup.dto.MemberRespDto;
import project.moseup.dto.teamPage.TeamDetailRespDto;
import project.moseup.repository.admin.AdminAskBoardReplyRepository;
import project.moseup.repository.admin.AdminFreeBoardRepository;
import project.moseup.repository.admin.AdminMemberRepository;
import project.moseup.repository.myPage.AskBoardInterfaceRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminIndexService {

    private final AdminMemberRepository adminMemberRepository;
    private final TeamInterfaceRepository teamInterfaceRepository;
    private final AdminAskBoardReplyRepository adminAskBoardReplyRepository;
    private final AskBoardInterfaceRepository askBoardInterfaceRepository;
    private final AdminFreeBoardRepository adminFreeBoardRepository;
    public Map<String, Object> getMemberAndTeamAndCount() {
        Map<String, Object> isResultMap = new HashMap<>();

        int teamSize = (int) teamInterfaceRepository.count();
        int category1 = getCategoryCount("습관");
        int category2 = getCategoryCount("공부");
        int category3 = getCategoryCount("운동");
        int category4 = getCategoryCount("기타");

        double categoryPercentage1 = getCategoryPercentage(category1, teamSize);
        double categoryPercentage2 = getCategoryPercentage(category2, teamSize);
        double categoryPercentage3 = getCategoryPercentage(category3, teamSize);
        double categoryPercentage4 = getCategoryPercentage(category4, teamSize);

        List<MemberRespDto> memberRespDtoList = adminMemberRepository
                .findTop5ByMemberDateOrderByMnoDesc(LocalDate.now())
                .stream()
                .map(memberPS -> new MemberRespDto().toDto(memberPS))
                .collect(Collectors.toList());

        List<TeamDetailRespDto> teamDetailRespDtoList = teamInterfaceRepository
                .findAll(Sort.by("tno"))
                .stream()
                .map(teamPS -> new TeamDetailRespDto().toDto(teamPS))
                .collect(Collectors.toList());

        List<FreeBoardRespDto> freeBoardRespDtoList = adminFreeBoardRepository
                .findTop5ByOrderByFnoDesc()
                .stream()
                .map(freeBoardPS -> new FreeBoardRespDto().toDto(freeBoardPS))
                .collect(Collectors.toList());

        List<AskBoardRespDto> askBoardRespDtoList = askBoardInterfaceRepository
                .findDistinctByAskBoardRepliesIsNullOrderByAno()
                .stream()
                .map(askBoardPS -> new AskBoardRespDto().toDto(askBoardPS))
                .collect(Collectors.toList());

        isResultMap.put("category1", String.format("%.1f", categoryPercentage1) + "%");
        isResultMap.put("category2", String.format("%.1f", categoryPercentage2) + "%");
        isResultMap.put("category3", String.format("%.1f", categoryPercentage3) + "%");
        isResultMap.put("category4", String.format("%.1f", categoryPercentage4) + "%");
        isResultMap.put("memberList", memberRespDtoList);
        isResultMap.put("teamList", teamDetailRespDtoList);
        isResultMap.put("freeBoardList", freeBoardRespDtoList);
        isResultMap.put("askBoardList", askBoardRespDtoList);


        return isResultMap;
    }


    // 카테고리별 갯수 구하기
    public int getCategoryCount(String name){
        int count = 0;
        count = teamInterfaceRepository.countByTeamCategory1(name);

        return count;
    }

    // 카테고리 퍼센트 구하기
    public double getCategoryPercentage(int categorySize, int teamSize){
        double percentage;

        percentage = (double) categorySize / (double) teamSize * 100.0;

        return percentage;
    }
}
