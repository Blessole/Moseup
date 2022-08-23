package project.moseup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.moseup.domain.Team;

@Repository
public interface MainInterfaceRepository extends JpaRepository<Team, Long>{

	List<Team> findTop5ByOrderByTeamVolumeDesc();
}
