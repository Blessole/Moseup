package project.moseup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.moseup.domain.Team;
import project.moseup.dto.TeamCreateReqDto;
import project.moseup.repository.TeamCreateRepository;
import project.moseup.repository.myPage.TeamInterfaceRepository;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamCreateService {

	private final TeamCreateRepository teamCreateRepository;
	private final TeamInterfaceRepository TeamInterfaceRepository;
	
	// 파일 업로드 경로
    @Value("${moseup.upload.path}") //application.properties의 변수
    private String uploadPath;

    //팀 생성
	@Transactional
	public Long create(TeamCreateReqDto teamCreateReqDto, MultipartFile file) throws Exception {
		
		//카테고리3 입력 안 했을 시 null값 주기
		if (teamCreateReqDto.getTeamCategory3().equals("")) {
			teamCreateReqDto.setTeamCategory3(null);
		}
		
		UUID uuid = UUID.randomUUID();	//고유식별자생성
		
		String fileName = uuid + "_" + file.getOriginalFilename(); //파일명
		
		//팀명 폴더 생성 - 해당 위치에 폴더가 없을 경우 생성하는 코드
		String folderName = teamCreateReqDto.getTeamName();
		String uploadPath2 = uploadPath + "/teamPhotos";
		File uploadPathFolder = new File(uploadPath2, folderName);
		if(!uploadPathFolder.exists()){
			uploadPathFolder.mkdirs(); //폴더 생성
        }

		File saveFile = new File(uploadPathFolder, fileName);	//uploadPathFolder 경로에 fileName이라는 이름으로 파일 생성
		
		file.transferTo(saveFile);	//파일 저장
		
		teamCreateReqDto.setTeamPhoto(fileName);
		teamCreateReqDto.setTeamJoiner(1);	//가입인원set
		
		Team team = teamCreateReqDto.teamBuilder();
		
		teamCreateRepository.save(team);
		
		return team.getTno();
	}

	//팀 단건 조회
	public Team findOne(Long tno) {
		return teamCreateRepository.findOne(tno);
	}
	
	//팀명 중복 체크
	public List<Team> validateDuplicateTeam(String teamName) {
		List<Team> findTeams = teamCreateRepository.findByName(teamName);
		if(!findTeams.isEmpty()) {
			List<Team> team = findTeams;
			return team;
		}
		return null;
	}

	public List<Team> teams() {
		return TeamInterfaceRepository.findAll();
	}
}