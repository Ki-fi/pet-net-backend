package petnet.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import petnet.com.models.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);

    @Query("""
        SELECT a FROM Account a
        JOIN FETCH a.user u
        WHERE a.email = :email
    """)

    Optional<Account> findByEmail(@Param("email") String email);


}
