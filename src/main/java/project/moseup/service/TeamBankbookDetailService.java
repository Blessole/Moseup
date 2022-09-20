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

import project.moseup.dto.TeamBankbookDetailWithdrawDto;
import project.moseup.repository.TeamBankbookDetailClassRepository;
import project.moseup.repository.TeamBankbookDetailInterfaceRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamBankbookDetailService {
	
	private final TeamBankbookDetailInterfaceRepository teamBankbookDetailInterfaceRepository;
	private final TeamBankbookDetailClassRepository teamBankbookDetailClassRepository;

	// 입금하기
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
	
	// 출금 하기
	@Transactional
	public void withdraw(TeamBankbook teamBankbook, Team team, Member member) {
		TeamBankbookDetailWithdrawDto teamBankBookDetailWithdrawDto = new TeamBankbookDetailWithdrawDto();
		
		// 마지막 거래 내역 조회(잔액 조회를 위한)
		List<TeamBankbookDetail> teamBankbookDetails = this.findTeamBankbookDetail(teamBankbook);
		
		// 잔액 조회(제일 마지막이 최종 금액이니까)
		int balanceMoney = teamBankbookDetails.get(teamBankbookDetails.size() - 1).getTeamBankbookTotal();
		
		//팀 예치금액
		int deposit = team.getTeamDeposit()*10000;	
		
		// 출금 로직
		teamBankBookDetailWithdrawDto.setTeamBankbook(teamBankbook);
		teamBankBookDetailWithdrawDto.setMember(member); // 회원 번호
		teamBankBookDetailWithdrawDto.setDealList(member.getNickname()+" 인증 출금"); // 거래 리스트
		teamBankBookDetailWithdrawDto.setTeamBankbookDeposit(0); // 입금액
		teamBankBookDetailWithdrawDto.setTeamBankbookWithdraw(deposit); // 출금액
		teamBankBookDetailWithdrawDto.setTeamBankbookTotal(balanceMoney-deposit); // 총액(팀 통장 총액 - 팀예치금)
		teamBankBookDetailWithdrawDto.setTeamBankbookDate(LocalDateTime.now()); // 거래(입출금) 일자
			
		TeamBankbookDetail teamBankbookDetail = teamBankBookDetailWithdrawDto.toEntity();
		teamBankbookDetailClassRepository.withDraw(teamBankbookDetail);		
	}
	
	// 팀 통장 총 거래 리스트
	public List<TeamBankbookDetail> findTeamBankbookDetailListByTeamBankbook(TeamBankbook teamBankbook){
		return teamBankbookDetailClassRepository.findAll(teamBankbook);
	}

}
