package petnet.com.mappers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import petnet.com.dtos.AccountInputDto;
import petnet.com.models.Account;
import petnet.com.models.User;
import petnet.com.models.UserRole;

@Component
public class AccountMapper {

    private final PasswordEncoder passwordEncoder;

    public AccountMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUserEntity(AccountInputDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName);
        user.setPreposition(dto.preposition);
        user.setLastName(dto.lastName);
        user.setRole(UserRole.USER);

        Account account = new Account();
        account.setEmail(dto.email);
        account.setPassword(passwordEncoder.encode(dto.password));
        account.setUser(user);

        user.setAccount(account);

        return user;
    }
}