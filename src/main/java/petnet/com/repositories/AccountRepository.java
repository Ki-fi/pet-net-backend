package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import petnet.com.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);

}
