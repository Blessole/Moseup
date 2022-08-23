package project.moseup.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.repository.MainInterfaceRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

	private final MainInterfaceRepository mainInterfaceRepository;
	
	//teamVolume 높은순 5개
	public List<Team> topList() {
//		List<Team> topList = mainRepository.findTopList();
		List<Team> topList = mainInterfaceRepository.findTop5ByOrderByTeamVolumeDesc();
		return topList;
	}

}
