package project.moseup.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.FreeBoard;

import java.util.List;

public interface AdminFreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    Page<FreeBoard> findByFreeTitleContainingOrFreeContentContainingOrMemberNicknameContaining(String keyword, String keyword1, String keyword2, Pageable pageable);


    List<FreeBoard> findAllByOrderByFnoDesc();

    List<FreeBoard> findTop5ByOrderByFnoDesc();
}
