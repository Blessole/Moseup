package project.moseup.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.moseup.domain.Bankbook;
import project.moseup.domain.Member;
import project.moseup.domain.Team;
import project.moseup.dto.BankbookSaveReqDto;
import project.moseup.repository.BankbookRepository;
import project.moseup.repository.myPage.BankbookInterfaceRepository;
import project.moseup.service.myPage.MyPageService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BankbookService {
    
    private final BankbookRepository bankbookRepository;
    private final MyPageService myPageService;

	/** 내 통장 조회 **/
	public Bankbook findMyBankbook(Long mno) {
		return bankbookRepository.findByMember(mno);
	}
	
	/** 출금하기 **/
	@Transactional
	public int withdraw(Member member, Team team) {
		List<Bankbook> myBankbook = myPageService.findBankbook(member);	// 통장 조회
		
		BankbookSaveReqDto bankbookDto = new BankbookSaveReqDto();
		
		int deposit = team.getTeamDeposit()*10000;	//팀 예치금액
		
		int result;
		
		if (myBankbook.get(0).getBankbookTotal() < deposit) {
			result = -1;	// 현재 가진 금액보다 팀 예치금이 큼
		} else {
			int totalMoney = myBankbook.get(0).getBankbookTotal();
			bankbookDto.setMember(member); // 회원 번호
			bankbookDto.setDealList(team.getTeamName()+" 가입 출금"); // 거래 리스트
			bankbookDto.setBankbookDeposit(0); // 입금액
			bankbookDto.setBankbookWithdraw(deposit); // 출금액
			bankbookDto.setBankbookTotal(totalMoney-deposit); // 총액(내 통장 총액 - 팀예치금)
			bankbookDto.setBankbookDate(LocalDateTime.now()); // 거래(입출금) 일자
			
			Bankbook bankbook = bankbookDto.toEntity();
			bankbookRepository.withdrawMerge(bankbook);
			result = 1;
		}
		
		return result;	
	}
	
}
