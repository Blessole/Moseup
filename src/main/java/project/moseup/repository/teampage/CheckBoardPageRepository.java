package project.moseup.repository.teampage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import project.moseup.domain.CheckBoard;
import project.moseup.domain.Team;

public interface CheckBoardPageRepository extends JpaRepository<CheckBoard, Long> {
	
	Page<CheckBoard> findByTeam(Team team, Pageable pageable);

}
