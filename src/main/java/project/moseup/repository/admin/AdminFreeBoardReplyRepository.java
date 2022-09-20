package project.moseup.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.FreeBoard;
import project.moseup.domain.FreeBoardReply;

import java.util.List;

public interface AdminFreeBoardReplyRepository extends JpaRepository<FreeBoardReply, Long> {


    List<FreeBoardReply> findByFreeBoardOrderByFrnoDesc(FreeBoard freeBoardPS);
}
