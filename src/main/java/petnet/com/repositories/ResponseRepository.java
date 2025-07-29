package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import petnet.com.models.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
