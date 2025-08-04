package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import petnet.com.dtos.AccountInputDto;
import petnet.com.exceptions.EmailAlreadyExistsException;
import petnet.com.models.Account;
import petnet.com.models.User;
import petnet.com.repositories.AccountRepository;
import petnet.com.repositories.UserRepository;

import static petnet.com.models.UserRole.USER;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(UserRepository userRepository,
                          AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createAccount(AccountInputDto dto) {

        if (accountRepository.existsByEmail(dto.email)) {
            throw new EmailAlreadyExistsException("Dit e-mailadres is al in gebruik.");
        }

        User user = new User();
        user.setFirstName(dto.firstName);
        user.setPreposition(dto.preposition);
        user.setLastName(dto.lastName);
        user.setRole(USER);

        Account account = new Account();
        account.setEmail(dto.email);
        account.setPassword(passwordEncoder.encode(dto.password));
        account.setUser(user);

        user.setAccount(account);

        userRepository.save(user);
    }
}

