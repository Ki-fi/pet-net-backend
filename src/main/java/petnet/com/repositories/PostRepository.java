package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import petnet.com.models.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
