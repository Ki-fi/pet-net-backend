package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import petnet.com.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
