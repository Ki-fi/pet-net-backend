package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import petnet.com.models.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);
    Optional<Account> findByEmail(String email);

}
