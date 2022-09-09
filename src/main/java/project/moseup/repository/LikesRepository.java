package project.moseup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Likes;
import project.moseup.domain.LikesId;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {
}
