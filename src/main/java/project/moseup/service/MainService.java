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
	
	//찜목록 많은순 best5
	public List<Team> topList() {
		List<Team> topList = mainInterfaceRepository.topList();
		return topList;
	}
	
	//카테고리1 공부 최신순
	public List<Team> studyTopList() {
		return mainInterfaceRepository.studyTopList();
	}
	//카테고리1 운동 최신순
	public List<Team> exerciseTopList() {
		List<Team> exerciseTopList = mainInterfaceRepository.exerciseTopList();
	return exerciseTopList;
	}
	//카테고리1 습관 최신순
	public List<Team> habitTopList() {
		List<Team> habitTopList = mainInterfaceRepository.habitTopList();
	return habitTopList;
	}
	//카테고리1 기타 최신순
	public List<Team> etcTopList() {
		List<Team> etcTopList = mainInterfaceRepository.etcTopList();
	return etcTopList;
	}
	//최근 생성팀 4개
	public List<Team> newTeamList() {
		List<Team> newTeamList = mainInterfaceRepository.findTop4ByOrderByTnoDesc();
		return newTeamList;
	}
}
