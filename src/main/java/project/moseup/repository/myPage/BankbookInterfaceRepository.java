package project.moseup.repository.myPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Bankbook;
import project.moseup.domain.Member;
import project.moseup.domain.TeamMember;
import project.moseup.dto.BankbookRespDto;

import java.util.List;

public interface BankbookInterfaceRepository extends JpaRepository<Bankbook, Long> {

//    String findTotal = "select b.bankbookTotal from Bankbook b where b.member = :member Order by rowId Desc Limit 1";
    Page<Bankbook> findByMember(Member member, Pageable pageable);

    List<Bankbook> findTop1ByMemberOrderByDnoDesc(Member member);
}
