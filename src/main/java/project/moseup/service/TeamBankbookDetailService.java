package project.moseup.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.domain.TeamBankbook;
import project.moseup.domain.TeamBankbookDetail;
import project.moseup.dto.TeamBankBookDetailReqDto;
import project.moseup.repository.TeamBankbookDetailClassRepository;
import project.moseup.repository.TeamBankbookDetailInterfaceRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamBankbookDetailService {
	
	private final TeamBankbookDetailInterfaceRepository teamBankbookDetailInterfaceRepository;
	private final TeamBankbookDetailClassRepository teamBankbookDetailClassRepository;

	@Transactional
	public void create(TeamBankbook teamBankbook, Team team, Member member) {
		TeamBankBookDetailReqDto teamBankBookDetailReqDto = new TeamBankBookDetailReqDto();
		
		// 마지막 거래 내역 조회
		List<TeamBankbookDetail> myTeamBankbookDetails = this.findTeamBankbookDetail(teamBankbook);
		
		int originMoney;
        if (myTeamBankbookDetails.isEmpty()){
            originMoney = 0;
        } else {
            originMoney = myTeamBankbookDetails.get(0).getTeamBankbookTotal();
        }
		
		int deposit = team.getTeamDeposit()*10000;	//팀 예치금액

		teamBankBookDetailReqDto.setTeamBankbook(teamBankbook);
		teamBankBookDetailReqDto.setMember(member);
		teamBankBookDetailReqDto.setDealList(member.getName()+" 입금");	//거래 리스트
		teamBankBookDetailReqDto.setTeamBankbookDeposit(deposit);	//입금액
		teamBankBookDetailReqDto.setTeamBankbookWithdraw(0);	//출금액
		teamBankBookDetailReqDto.setTeamBankbookTotal(originMoney+deposit);	//총액
		teamBankBookDetailReqDto.setTeamBankbookDate(LocalDateTime.now()); //거래일
		
		TeamBankbookDetail teamBankbookDetail = teamBankBookDetailReqDto.toEntity();
		
		teamBankbookDetailClassRepository.save(teamBankbookDetail);
	}

	/** 팀 통장 거래 리스트 조회 **/
	public List<TeamBankbookDetail> findTeamBankbookDetail(TeamBankbook teamBankbook) {
		return teamBankbookDetailInterfaceRepository.findTop1ByTeamBankbookOrderByTdnoDesc(teamBankbook);
	}
	
	// 팀 통장 총 거래 리스트
	public List<TeamBankbookDetail> findTeamBankbookDetailListByTeamBankbook(TeamBankbook teamBankbook){
		return teamBankbookDetailClassRepository.findAll(teamBankbook);
	}

}
