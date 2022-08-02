package project.moseup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.moseup.domain.Team;

public interface TeamSearchRepository extends JpaRepository<Team, Long>{

	List<Team> findByteamNameContaining(String keyword);

}
