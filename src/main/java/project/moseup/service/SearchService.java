package project.moseup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.repository.SearchRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
	
	private final SearchRepository searchRepository;

	public Page<Team> searchedFilterList(String keyword, String filter1, String filter2, Pageable pageable) {
		System.out.println("키워드 2번 = " + keyword);
		System.out.println("필터1 2번 = " + filter1);
		System.out.println("필터2 2번 = " + filter2);
		
		if (filter2.equals("최신순")) {	// 필터2만 선택
			Page<Team> filter2NewList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2NewList;
		} else if (filter2.equals("팀원수순")) {	// 필터2만 선택
			Page<Team> filter2MemberList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2MemberList;
		} else if (filter2.equals("예치금순")) {	// 필터2만 선택
			Page<Team> filter2depositList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
		} else if (filter2.equals("날짜순")) {	// 필터2만 선택
			Page<Team> filter2depositList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
		}	else if (filter1.equals("팀명")) {		// 필터1만 선택
			Page<Team> filter1TeamNameList = searchRepository.findByTeamNameContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamNameList;
		}
		return null;
	}
	
}
