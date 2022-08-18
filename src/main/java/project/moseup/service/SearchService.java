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
		if ( (filter2.equals("nothing") || filter2.equals("")) && (filter1.equals("nothing") || filter1.equals("")) ) {
			Page<Team> filterNothingList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filterNothingList;
		} else if (filter2.equals("최신순") && filter1.equals("")) {	//필터2 최신순만 검색
			Page<Team> filter2NewList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2NewList;
		} else if (filter2.equals("팀원수순") && filter1.equals("")) {	//필터2 팀원수순만 검색
			Page<Team> filter2MemberList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2MemberList;
		} else if (filter2.equals("예치금순") && filter1.equals("")) {	//필터2 예치금순만 검색
			Page<Team> filter2depositList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
		} else if (filter2.equals("시작일순") && filter1.equals("")) {	//필터2 시작일순만 검색
			Page<Team> filter2depositList = searchRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
			
		} else if (filter1.equals("팀명") && filter2.equals("nothing")) {		//필터1 팀명만 검색
			Page<Team> filter1TeamNameList = searchRepository.findByTeamNameContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamNameList;
		} else if (filter1.equals("팀장명") && filter2.equals("nothing")) {		//필터1 팀장명만 검색
			Page<Team> filter1TeamLeaderList = searchRepository.findByTeamLeaderContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamLeaderList;
		} else if (filter1.equals("태그") && filter2.equals("nothing")) {		//필터1 태그만 검색
			Page<Team> filter1CategoryList = searchRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, pageable);
			return filter1CategoryList;
			
		} else if (filter1.equals("팀명,팀장명") && filter2.equals("nothing")) {		//필터1 팀명/팀장명 검색
			Page<Team> filter1TeamNameOrTeamLeaderList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTnoDesc(keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("nothing")) {		//필터1 팀명/태그 검색
			Page<Team> filter1TeamNameOrCategoryList = searchRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrCategoryList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("nothing")) {		//필터1 팀장명/태그 검색
			Page<Team> filter1TeamLeaderOrCategoryList = searchRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("nothing")) {		//필터1 팀명/팀장명/태그 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryList;
			
		} else if (filter1.equals("팀명") && filter2.equals("최신순")) {		//필터1 팀명, 필터2 최신순 검색
			Page<Team> filter1TeamNameFilter2NewList = searchRepository.findByTeamNameContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamNameFilter2NewList;
		} else if (filter1.equals("팀명") && filter2.equals("시작일순")) {		//필터1 팀명, 필터2 시작일순 검색
			Page<Team> filter1TeamNameFilter2DateList = searchRepository.findByTeamNameContainingOrderByStartDateAsc(keyword, pageable);
			return filter1TeamNameFilter2DateList;
		} else if (filter1.equals("팀명") && filter2.equals("팀원수순")) {		//필터1 팀명, 필터2 팀원수순 검색
			Page<Team> filter1TeamNameFilter2TeamVolumeList = searchRepository.findByTeamNameContainingOrderByTeamVolumeDesc(keyword, pageable);
			return filter1TeamNameFilter2TeamVolumeList;
		} else if (filter1.equals("팀명") && filter2.equals("예치금순")) {		//필터1 팀명, 필터2 예치금순 검색
			Page<Team> filter1TeamNameFilter2TeamDepositList = searchRepository.findByTeamNameContainingOrderByTeamDepositAsc(keyword, pageable);
			return filter1TeamNameFilter2TeamDepositList;
			
			
		} else if (filter1.equals("팀장명") && filter2.equals("최신순")) {		//필터1 팀장명, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderFilter2NewList = searchRepository.findByTeamLeaderContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamLeaderFilter2NewList;
		} else if (filter1.equals("팀장명") && filter2.equals("시작일순")) {		//필터1 팀장명, 필터2 시작일순 검색
			Page<Team> filter1TeamLeaderFilter2DateList = searchRepository.findByTeamLeaderContainingOrderByStartDateAsc(keyword, pageable);
			return filter1TeamLeaderFilter2DateList;
		} else if (filter1.equals("팀장명") && filter2.equals("팀원수순")) {		//필터1 팀장명, 필터2 팀원수순 검색
			Page<Team> filter1TeamLeaderFilter2TeamVolumeList = searchRepository.findByTeamLeaderContainingOrderByTeamVolumeDesc(keyword, pageable);
			return filter1TeamLeaderFilter2TeamVolumeList;
		} else if (filter1.equals("팀장명") && filter2.equals("예치금순")) {		//필터1 팀장명, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderFilter2TeamDepositList = searchRepository.findByTeamLeaderContainingOrderByTeamDepositAsc(keyword, pageable);
			return filter1TeamLeaderFilter2TeamDepositList;
			
			
		} else if (filter1.equals("태그") && filter2.equals("최신순")) {		//필터1 태그, 필터2 최신순 검색
			Page<Team> filter1CategoryFilter2NewtList = searchRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2NewtList;
		} else if (filter1.equals("태그") && filter2.equals("시작일순")) {		//필터1 태그, 필터2 시작일순 검색
			Page<Team> filter1CategoryFilter2DatetList = searchRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2DatetList;
		} else if (filter1.equals("태그") && filter2.equals("팀원수순")) {		//필터1 태그, 필터2 팀원수순 검색
			Page<Team> filter1CategoryFilter2VolumetList = searchRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2VolumetList;
		} else if (filter1.equals("태그") && filter2.equals("예치금순")) {		//필터1 태그, 필터2 예치금순 검색
			Page<Team> filter1CategoryFilter2TeamDepositList = searchRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2TeamDepositList;
			
		} else if (filter1.equals("팀명,팀장명") && filter2.equals("최신순")) {		//필터1 팀명/팀장명, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2NewList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTnoDesc(keyword, keyword, pageable);
			return filter1CategoryOrTeamLeaderFilter2NewList;
		}  else if (filter1.equals("팀명,팀장명") && filter2.equals("시작일순")) {		//필터1 팀명/팀장명, 필터2 시작일순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2DateList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByStartDateAsc(keyword, keyword ,pageable);
			return filter1CategoryOrTeamLeaderFilter2DateList;
		}  else if (filter1.equals("팀명,팀장명") && filter2.equals("팀원수순")) {		//필터1 팀명/팀장명, 필터2 팀원수순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2TeamVolumeList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTeamVolumeDesc(keyword, keyword ,pageable);
			return filter1CategoryOrTeamLeaderFilter2TeamVolumeList;
		} else if (filter1.equals("팀명,팀장명") && filter2.equals("예치금순")) {		//필터1 팀명/팀장명, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2TeamDepositList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTeamDepositAsc(keyword, keyword ,pageable);
			return filter1CategoryOrTeamLeaderFilter2TeamDepositList;
		
		} else if (filter1.equals("팀명,태그") && filter2.equals("최신순")) {		//필터1 팀명/태그, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrCategoryFilter2NewList = searchRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2NewList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("시작일순")) {		//필터1 팀명/태그, 필터2 시작일순 검색
			Page<Team> filter1CategoryOrCategoryFilter2DateList = searchRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2DateList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("팀원수순")) {		//필터1 팀명/태그, 필터2 팀원수순 검색
			Page<Team> filter1CategoryOrCategoryFilter2TeamVolumeList = searchRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2TeamVolumeList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("예치금순")) {		//필터1 팀명/태그, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrCategoryFilter2TeamDepositList = searchRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2TeamDepositList;
		
		} else if (filter1.equals("팀장명,태그") && filter2.equals("최신순")) {		//필터1 팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2NewList = searchRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2NewList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("시작일순")) {		//필터1 팀장명/태그, 필터2 시작일순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2DateList = searchRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2DateList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("팀원수순")) {		//필터1 팀장명/태그, 필터2 팀원수순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2VolumeList = searchRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2VolumeList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("예치금순")) {		//필터1 팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2DepositList = searchRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2DepositList;
		
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("최신순")) {		//필터1 팀장/팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2NewList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2NewList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("시작일순")) {		//필터1 팀장/팀장명/태그, 필터2 시작일순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2DateList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2DateList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("팀원수순")) {		//필터1 팀장/팀장명/태그, 필터2 팀원수순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamVolumeList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamVolumeDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamVolumeList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("예치금순")) {		//필터1 팀장/팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamDepositList = searchRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamDepositList;
		}
		
		return null;
	}
	
}
