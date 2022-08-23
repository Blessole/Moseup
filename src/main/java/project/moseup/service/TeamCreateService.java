package project.moseup.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Team;
import project.moseup.dto.TeamCreateReqDto;
import project.moseup.repository.TeamCreateRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamCreateService {

	private final TeamCreateRepository teamCreateRepository;
//	private final TeamCreateRespDto teamCreateRespDto;
	
	// 파일 업로드 경로
    @Value("${moseup.upload.path}") //application.properties의 변수
    private String uploadPath;

    //팀 생성
	@Transactional
	public Long create(TeamCreateReqDto teamCreateReqDto, MultipartFile file) throws Exception {
		
//		//팀명 폴더 생성 - 해당 위치에 폴더가 없을 경우 생성하는 코드
//        String teamName = teamCreateReqDto.getTeamName();
//        File uploadPathFolder = new File(uploadPath, teamName);	//uploadPath라는 경로에 teamName이라는 이름으로 폴더 생성
//        if(!uploadPathFolder.exists()){
//        	uploadPathFolder.mkdirs(); //폴더 생성
//        }
//		
//		//파일 경로 저장
//        UUID uuid = UUID.randomUUID();	//고유식별자생성
//        String fileName = uploadPath + File.separator + teamName + File.separator + uuid + "_" + file.getOriginalFilename(); // 경로 + 폴더명
//        Path savePath = Paths.get(fileName);	//경로 정의
//		file.transferTo(savePath);	//파일 저장
//
//		teamCreateReqDto.setTeamPhoto(fileName);
//		
//		Team team = teamCreateReqDto.teamBuilder();
//		
//		createTeamRepository.save(team);
		
		
		
		
		UUID uuid = UUID.randomUUID();	//고유식별자생성
		
		String fileName = uuid + "_" + file.getOriginalFilename(); //파일명

		File saveFile = new File(uploadPath, fileName);	//uploadPath라는 경로에 fileName이라는 이름으로 폴더 생성
		
//		Path savePath = Paths.get(uploadPath);	//경로 정의
		
		file.transferTo(saveFile);	//파일 저장
		
		teamCreateReqDto.setTeamPhoto(fileName);
		
		Team team = teamCreateReqDto.teamBuilder();
		
		teamCreateRepository.save(team);
		
		return team.getTno();
		
		
		
		
//		String originalName = file.getOriginalFilename();
//		String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
//		
//		//팀명 폴더 생성 - 해당 위치에 폴더가 없을 경우 생성하는 코드
//		String folderName = teamCreateReqDto.getTeamName();
//		File uploadPathFolder = new File(uploadPath, folderName);
//		if(!uploadPathFolder.exists()){
//			uploadPathFolder.mkdirs(); //폴더 생성
//        }
//		
//		//파일 경로 저장하기
//        String uuid = UUID.randomUUID().toString()+".jpg";
//        System.out.println("유유아이디 = " + uuid);
//        String saveName = uploadPath + File.separator + folderName + File.separator + uuid + "_" + fileName; //경로 + 폴더명
//        Path savePath = Paths.get(saveName);	//경로 정의
//        file.transferTo(savePath);
//        
//        //팀 저장하기
//        teamCreateReqDto.setTeamPhoto(saveName);
//		Team team = teamCreateReqDto.teamBuilder();	
//		
//		teamCreateRepository.save(team);
//		
//		return team.getTno();
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

}