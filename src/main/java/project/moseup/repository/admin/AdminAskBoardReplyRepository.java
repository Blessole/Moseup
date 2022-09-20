package project.moseup.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.AskBoardReply;

public interface AdminAskBoardReplyRepository extends JpaRepository<AskBoardReply, Long> {
}
