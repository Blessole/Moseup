package project.moseup.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.FreeBoard;

public interface AdminFreeBoardRepository extends JpaRepository<FreeBoard, Long> {

}
