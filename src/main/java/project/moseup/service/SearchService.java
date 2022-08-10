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

	public Page<Team> searchedfilter2List(String keyword, String filter2, Pageable pageable) {
		
		if (filter2.equals("최신순")) {
			Page<Team> filter2NewList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2NewList;
		} else if (filter2.equals("팀원수순")) {
			Page<Team> filter2MemberList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2MemberList;
		} else if (filter2.equals("예치금순")) {
			Page<Team> filter2depositList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
		} else if (filter2.equals("날짜순")) {
			Page<Team> filter2depositList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
		}
		return null;
	}
	
//		//keyword가 포함된 모든팀 찾기
//		public List<Team> findKeywordAll(String keyword, Pageable pageable) {
//			List<Team> findAllList = SearchRepository.findAllSearch(keyword, pageable);
//			
////			List<Team> emptyList = new ArrayList<>();
//			if (!findAllList.isEmpty()) {
//				return findAllList;
//			}
//			return null;
//		}

}
