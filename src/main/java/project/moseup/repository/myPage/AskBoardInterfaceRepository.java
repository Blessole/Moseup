package project.moseup.repository.myPage;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.AskBoard;

public interface AskBoardInterfaceRepository extends JpaRepository<AskBoard, Long> {
}
