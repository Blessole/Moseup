package project.moseup.repository.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moseup.domain.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {

}
