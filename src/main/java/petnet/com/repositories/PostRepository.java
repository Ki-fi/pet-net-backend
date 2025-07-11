package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import petnet.com.models.Post;
import petnet.com.models.User;

public interface PostRepository extends JpaRepository<Post, Long>{

}
