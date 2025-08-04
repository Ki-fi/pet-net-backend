package petnet.com.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import petnet.com.models.Account;
import petnet.com.repositories.AccountRepository;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

   @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Geen account gevonden met dit emailadres"));

        account.getUser().getUserRole();

       System.out.println("Account class: " + account.getClass().getName());
       System.out.println("User class: " + account.getUser().getClass().getName());
       System.out.println("Role: " + account.getUser().getUserRole());


       return new CustomUserDetails(account);
    }

}
