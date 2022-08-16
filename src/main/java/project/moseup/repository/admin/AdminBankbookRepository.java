package project.moseup.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Bankbook;

public interface AdminBankbookRepository extends JpaRepository<Bankbook, Long> {
}
