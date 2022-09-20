package project.moseup.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.repository.SearchInterfaceRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
	
	private final SearchInterfaceRepository searchInterfaceRepository;

	public Page<Team> searchedFilterList(String keyword, String filter1, String filter2, Pageable pageable) {
		if (filter2.equals("nothing") && filter1.equals("nothing")) {
			Page<Team> filterNothingList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filterNothingList;
		} else if (filter2.equals("최신순") && filter1.equals("nothing")) {	//필터2 최신순만 검색
			Page<Team> filter2NewList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2NewList;
		} else if (filter2.equals("팀원수순") && filter1.equals("nothing")) {	//필터2 팀원수순만 검색
			Page<Team> filter2MemberList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2MemberList;
		} else if (filter2.equals("예치금순") && filter1.equals("nothing")) {	//필터2 예치금순만 검색
			Page<Team> filter2depositList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
		} else if (filter2.equals("시작일순") && filter1.equals("nothing")) {	//필터2 시작일순만 검색
			Page<Team> filter2depositList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter2depositList;
			
		} else if (filter1.equals("팀명") && filter2.equals("nothing")) {		//필터1 팀명만 검색
			Page<Team> filter1TeamNameList = searchInterfaceRepository.findByTeamNameContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamNameList;
		} else if (filter1.equals("팀장명") && filter2.equals("nothing")) {		//필터1 팀장명만 검색
			Page<Team> filter1TeamLeaderList = searchInterfaceRepository.findByTeamLeaderContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamLeaderList;
		} else if (filter1.equals("태그") && filter2.equals("nothing")) {		//필터1 태그만 검색
			Page<Team> filter1CategoryList = searchInterfaceRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, pageable);
			return filter1CategoryList;
			
		} else if (filter1.equals("팀명,팀장명") && filter2.equals("nothing")) {		//필터1 팀명/팀장명 검색
			Page<Team> filter1TeamNameOrTeamLeaderList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTnoDesc(keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("nothing")) {		//필터1 팀명/태그 검색
			Page<Team> filter1TeamNameOrCategoryList = searchInterfaceRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrCategoryList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("nothing")) {		//필터1 팀장명/태그 검색
			Page<Team> filter1TeamLeaderOrCategoryList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("nothing")) {		//필터1 팀명/팀장명/태그 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryList;
			
		} else if (filter1.equals("팀명") && filter2.equals("최신순")) {		//필터1 팀명, 필터2 최신순 검색
			Page<Team> filter1TeamNameFilter2NewList = searchInterfaceRepository.findByTeamNameContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamNameFilter2NewList;
		} else if (filter1.equals("팀명") && filter2.equals("시작일순")) {		//필터1 팀명, 필터2 시작일순 검색
			Page<Team> filter1TeamNameFilter2DateList = searchInterfaceRepository.findByTeamNameContainingOrderByStartDateAsc(keyword, pageable);
			return filter1TeamNameFilter2DateList;
		} else if (filter1.equals("팀명") && filter2.equals("팀원수순")) {		//필터1 팀명, 필터2 팀원수순 검색
			Page<Team> filter1TeamNameFilter2TeamVolumeList = searchInterfaceRepository.findByTeamNameContainingOrderByTeamJoinerDesc(keyword, pageable);
			return filter1TeamNameFilter2TeamVolumeList;
		} else if (filter1.equals("팀명") && filter2.equals("예치금순")) {		//필터1 팀명, 필터2 예치금순 검색
			Page<Team> filter1TeamNameFilter2TeamDepositList = searchInterfaceRepository.findByTeamNameContainingOrderByTeamDepositAsc(keyword, pageable);
			return filter1TeamNameFilter2TeamDepositList;
			
			
		} else if (filter1.equals("팀장명") && filter2.equals("최신순")) {		//필터1 팀장명, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderFilter2NewList = searchInterfaceRepository.findByTeamLeaderContainingOrderByTnoDesc(keyword, pageable);
			return filter1TeamLeaderFilter2NewList;
		} else if (filter1.equals("팀장명") && filter2.equals("시작일순")) {		//필터1 팀장명, 필터2 시작일순 검색
			Page<Team> filter1TeamLeaderFilter2DateList = searchInterfaceRepository.findByTeamLeaderContainingOrderByStartDateAsc(keyword, pageable);
			return filter1TeamLeaderFilter2DateList;
		} else if (filter1.equals("팀장명") && filter2.equals("팀원수순")) {		//필터1 팀장명, 필터2 팀원수순 검색
			Page<Team> filter1TeamLeaderFilter2TeamVolumeList = searchInterfaceRepository.findByTeamLeaderContainingOrderByTeamJoinerDesc(keyword, pageable);
			return filter1TeamLeaderFilter2TeamVolumeList;
		} else if (filter1.equals("팀장명") && filter2.equals("예치금순")) {		//필터1 팀장명, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderFilter2TeamDepositList = searchInterfaceRepository.findByTeamLeaderContainingOrderByTeamDepositAsc(keyword, pageable);
			return filter1TeamLeaderFilter2TeamDepositList;
			
			
		} else if (filter1.equals("태그") && filter2.equals("최신순")) {		//필터1 태그, 필터2 최신순 검색
			Page<Team> filter1CategoryFilter2NewtList = searchInterfaceRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2NewtList;
		} else if (filter1.equals("태그") && filter2.equals("시작일순")) {		//필터1 태그, 필터2 시작일순 검색
			Page<Team> filter1CategoryFilter2DatetList = searchInterfaceRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2DatetList;
		} else if (filter1.equals("태그") && filter2.equals("팀원수순")) {		//필터1 태그, 필터2 팀원수순 검색
			Page<Team> filter1CategoryFilter2VolumetList = searchInterfaceRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2VolumetList;
		} else if (filter1.equals("태그") && filter2.equals("예치금순")) {		//필터1 태그, 필터2 예치금순 검색
			Page<Team> filter1CategoryFilter2TeamDepositList = searchInterfaceRepository.findByTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword ,pageable);
			return filter1CategoryFilter2TeamDepositList;
			
		} else if (filter1.equals("팀명,팀장명") && filter2.equals("최신순")) {		//필터1 팀명/팀장명, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2NewList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTnoDesc(keyword, keyword, pageable);
			return filter1CategoryOrTeamLeaderFilter2NewList;
		}  else if (filter1.equals("팀명,팀장명") && filter2.equals("시작일순")) {		//필터1 팀명/팀장명, 필터2 시작일순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2DateList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByStartDateAsc(keyword, keyword ,pageable);
			return filter1CategoryOrTeamLeaderFilter2DateList;
		}  else if (filter1.equals("팀명,팀장명") && filter2.equals("팀원수순")) {		//필터1 팀명/팀장명, 필터2 팀원수순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2TeamVolumeList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTeamJoinerDesc(keyword, keyword ,pageable);
			return filter1CategoryOrTeamLeaderFilter2TeamVolumeList;
		} else if (filter1.equals("팀명,팀장명") && filter2.equals("예치금순")) {		//필터1 팀명/팀장명, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrTeamLeaderFilter2TeamDepositList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrderByTeamDepositAsc(keyword, keyword ,pageable);
			return filter1CategoryOrTeamLeaderFilter2TeamDepositList;
		
		} else if (filter1.equals("팀명,태그") && filter2.equals("최신순")) {		//필터1 팀명/태그, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrCategoryFilter2NewList = searchInterfaceRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2NewList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("시작일순")) {		//필터1 팀명/태그, 필터2 시작일순 검색
			Page<Team> filter1CategoryOrCategoryFilter2DateList = searchInterfaceRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2DateList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("팀원수순")) {		//필터1 팀명/태그, 필터2 팀원수순 검색
			Page<Team> filter1CategoryOrCategoryFilter2TeamVolumeList = searchInterfaceRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2TeamVolumeList;
		} else if (filter1.equals("팀명,태그") && filter2.equals("예치금순")) {		//필터1 팀명/태그, 필터2 예치금순 검색
			Page<Team> filter1CategoryOrCategoryFilter2TeamDepositList = searchInterfaceRepository.findByTeamNameContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1CategoryOrCategoryFilter2TeamDepositList;
		
		} else if (filter1.equals("팀장명,태그") && filter2.equals("최신순")) {		//필터1 팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2NewList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2NewList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("시작일순")) {		//필터1 팀장명/태그, 필터2 시작일순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2DateList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2DateList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("팀원수순")) {		//필터1 팀장명/태그, 필터2 팀원수순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2VolumeList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2VolumeList;
		} else if (filter1.equals("팀장명,태그") && filter2.equals("예치금순")) {		//필터1 팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamLeaderOrCategoryFilter2DepositList = searchInterfaceRepository.findByTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, pageable);
			return filter1TeamLeaderOrCategoryFilter2DepositList;
		
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("최신순")) {		//필터1 팀장/팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2NewList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTnoDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2NewList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("시작일순")) {		//필터1 팀장/팀장명/태그, 필터2 시작일순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2DateList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByStartDateAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2DateList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("팀원수순")) {		//필터1 팀장/팀장명/태그, 필터2 팀원수순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamVolumeList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamJoinerDesc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamVolumeList;
		} else if (filter1.equals("팀명,팀장명,태그") && filter2.equals("예치금순")) {		//필터1 팀장/팀장명/태그, 필터2 예치금순 검색
			Page<Team> filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamDepositList = searchInterfaceRepository.findByTeamNameContainingOrTeamLeaderContainingOrTeamCategory1ContainingOrTeamCategory2ContainingOrTeamCategory3ContainingOrderByTeamDepositAsc(keyword, keyword, keyword, keyword, keyword, pageable);
			return filter1TeamNameOrTeamLeaderOrCategoryFilter2TeamDepositList;
		}
		
		//메인페이지 카테고리1별 인기순
		if (filter1.equals("") && filter2.equals("팀원수순")) {
			if (keyword.equals("공부")) {
				Page<Team> studyTopList = searchInterfaceRepository.findByTeamCategory1OrderByTeamJoinerDesc(keyword, pageable);
				return studyTopList;
			} else if (keyword.equals("운동")) {
				Page<Team> exerciseTopList = searchInterfaceRepository.findByTeamCategory1OrderByTeamJoinerDesc(keyword, pageable);
				return exerciseTopList;
			} else if (keyword.equals("습관")) {
				Page<Team> habitTopList = searchInterfaceRepository.findByTeamCategory1OrderByTeamJoinerDesc(keyword, pageable);
				return habitTopList;
			}
		}
		
		//메인페이지 최신순 24개
		if (keyword.equals("") && filter1.equals("") && filter2.equals("최신순")) {
			Page<Team> newTeamList = searchInterfaceRepository.queryFirst24ByOrderByTnoDesc(pageable);
			return newTeamList;
		}
		return null;
	}
	
}
