package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petnet.com.dtos.AccountInputDto;
import petnet.com.models.Account;
import petnet.com.models.User;
import petnet.com.repositories.AccountRepository;
import petnet.com.repositories.UserRepository;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void createAccount(AccountInputDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName);
        user.setPreposition(dto.preposition);
        user.setLastName(dto.lastName);

        Account account = new Account();
        account.setEmail(dto.email);
        account.setPassword(dto.password);
        account.setUser(user);

        user.setAccount(account);

        userRepository.save(user);
    }

}
