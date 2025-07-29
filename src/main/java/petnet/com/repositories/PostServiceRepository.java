package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import petnet.com.models.PostService;

public interface PostServiceRepository extends JpaRepository<PostService, Long> {
}
