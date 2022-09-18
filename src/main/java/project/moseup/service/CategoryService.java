package project.moseup.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.repository.CategoryInterfaceRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryInterfaceRepository categoryInterfaceRepository;

	
	public Page<Team> searchedFilterList(String keyword, String deposit, String volume, String notInclud, String filter2, Pageable pageable) {
		if (deposit == null && volume == null && notInclud == null && filter2 == null) {	//기본 리스트
			Page<Team> basicList = categoryInterfaceRepository.findByTeamCategory1ContainingOrderByTeamJoinerDesc(keyword, pageable);
			return basicList;
		} 
		
		else if (deposit != null && volume == null && notInclud == null) { //필터1 예치금만
			Page<Team> depositList = categoryInterfaceRepository.depositList(keyword, deposit, pageable);
			return depositList;
		}else if (deposit == null && volume != null && notInclud == null) { //필터1 모집인원만
			Page<Team> volumeList = categoryInterfaceRepository.volumeList(keyword, volume, pageable);
			return volumeList;
		} else if(deposit == null && volume == null && notInclud != null) {	//필터1 미포함만
			Page<Team> notIncludList = categoryInterfaceRepository.notIncludList(keyword, pageable);
			return notIncludList;
		}
		
		else if (deposit == null && volume == null && notInclud == null && filter2 != null) {	//필터2 최신순만
			if (filter2.equals("최신순")) {
				Page<Team> tnoList = categoryInterfaceRepository.findByTeamCategory1ContainingOrderByTnoDesc(keyword, pageable);
				return tnoList;
			} else if (filter2.equals("팀원수순")) {
				Page<Team> joinerList = categoryInterfaceRepository.findByTeamCategory1ContainingOrderByTeamJoinerDesc(keyword, pageable);
				return joinerList;
			}
		}
		
		else if (deposit != null && volume != null && notInclud == null && filter2 == null) {	//필터1 예치금/모집인원
			Page<Team> depositVolumeList = categoryInterfaceRepository.depositVolumeList(keyword, deposit, volume, pageable);
			return depositVolumeList;
		} else if(deposit != null && volume == null && notInclud != null && filter2 == null) {	//필터1 예치금/미포함
			Page<Team> depositNotIncludList = categoryInterfaceRepository.depositNotIncludList(keyword, deposit, pageable);
			return depositNotIncludList;
		} else if(deposit == null && volume != null && notInclud != null && filter2 == null) {	//필터1 모집인원/미포함
			Page<Team> volumeNotIncludList = categoryInterfaceRepository.volumeNotIncludList(keyword, volume, pageable);
			return volumeNotIncludList;
		} else if(deposit != null && volume != null && notInclud != null && filter2 == null) {	//필터1 예치금/모집인원/미포함
			Page<Team> filter1AllList = categoryInterfaceRepository.filter1AllList(keyword, deposit, volume, pageable);
			return filter1AllList;
		}
		
		else if (deposit != null && volume != null && notInclud == null && (filter2.equals("최신순") || filter2.equals("팀원수순"))) {	//필터1 예치금/모집인원, 필터2 최신순or팀원수순
			if (filter2.equals("최신순")) {
				Page<Team> depositVolumeNewList = categoryInterfaceRepository.depositVolumeNewList(keyword, deposit, volume, pageable);
				return depositVolumeNewList;
			} else if (filter2.equals("팀원수순")) {
				Page<Team> depositVolumeJoinerList = categoryInterfaceRepository.depositVolumeJoinerList(keyword, deposit, volume, pageable);
				return depositVolumeJoinerList;
			}
		} else if(deposit != null && volume == null && notInclud != null && (filter2.equals("최신순") || filter2.equals("팀원수순"))) {	//필터1 예치금/미포함, 필터2 최신순or팀원수순
			if (filter2.equals("최신순")) {
				Page<Team> depositNotIncludNewList = categoryInterfaceRepository.depositNotIncludNewList(keyword, deposit, pageable);
				return depositNotIncludNewList;
			} else if (filter2.equals("팀원수순")) {
				Page<Team> depositNotIncludJoinerList = categoryInterfaceRepository.depositNotIncludJoinerList(keyword, deposit, pageable);
				return depositNotIncludJoinerList;
			}
		} else if(deposit == null && volume != null && notInclud != null && (filter2.equals("최신순") || filter2.equals("팀원수순"))) {	//필터1 모집인원/미포함, 필터2 최신순or팀원수순
			if (filter2.equals("최신순")) {
				Page<Team> volumeNotIncludNewList = categoryInterfaceRepository.volumeNotIncludNewList(keyword, volume, pageable);
				return volumeNotIncludNewList;
			} else if (filter2.equals("팀원수순")) {
				Page<Team> volumeNotIncludJoinerList = categoryInterfaceRepository.volumeNotIncludJoinerList(keyword, volume, pageable);
				return volumeNotIncludJoinerList;
			}
		} else if(deposit != null && volume != null && notInclud != null && (filter2.equals("최신순") || filter2.equals("팀원수순"))) {	//필터1 예치금/모집인원/미포함, 필터2 최신순or팀원수순
			if (filter2.equals("최신순")) {
				Page<Team> filter1AllNewList = categoryInterfaceRepository.filter1AllNewList(keyword, deposit, volume, pageable);
				return filter1AllNewList;
			} else if (filter2.equals("팀원수순")) {
				Page<Team> filter1AllJoinerList = categoryInterfaceRepository.filter1AllJoinerList(keyword, deposit, volume, pageable);
				return filter1AllJoinerList;
			}
		}
		return null;
		
	}

	//best3 습관(찜 많은 순으로 바꿔야함)
	public List<Team> topList(String keyword) {
		List<Team> topList = categoryInterfaceRepository.topList(keyword);
		return topList;
	}

}