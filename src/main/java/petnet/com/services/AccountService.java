package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petnet.com.dtos.AccountInputDto;
import petnet.com.exceptions.EmailAlreadyExistsException;
import petnet.com.mappers.AccountMapper;
import petnet.com.models.User;
import petnet.com.repositories.AccountRepository;
import petnet.com.repositories.UserRepository;


@Service
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(UserRepository userRepository,
                          AccountRepository accountRepository,
                          AccountMapper accountMapper) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public void createAccount(AccountInputDto dto) {

        if (accountRepository.existsByEmail(dto.email)) {
            throw new EmailAlreadyExistsException("Dit e-mailadres is al in gebruik.");
        }

        User user = accountMapper.toUserEntity(dto);
        userRepository.save(user);
    }
}

