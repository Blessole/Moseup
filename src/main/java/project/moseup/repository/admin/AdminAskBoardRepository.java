package project.moseup.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.AskBoard;

public interface AdminAskBoardRepository extends JpaRepository<AskBoard, Long> {

}
