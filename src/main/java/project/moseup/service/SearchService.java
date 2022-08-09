package project.moseup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.repository.SearchRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
	
	private final SearchRepository SearchRepository;
	
		//keyword가 포함된 모든팀 찾기
		public List<Team> findKeywordAll(String keyword) {
			List<Team> findAllList = SearchRepository.findAllSearch(keyword);
			
			List<Team> emptyList = new ArrayList<>();
			if (!findAllList.isEmpty()) {
				List<Team> searchedTeamList = findAllList;
				return searchedTeamList;
			}
			return emptyList;
		}

}
