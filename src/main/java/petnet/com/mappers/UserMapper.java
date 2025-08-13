package petnet.com.mappers;

import org.springframework.stereotype.Component;
import petnet.com.dtos.ProfileOutputDto;
import petnet.com.models.User;

@Component
public class UserMapper {

    public ProfileOutputDto toProfileOutput(User user) {

        ProfileOutputDto dto = new ProfileOutputDto();
        dto.email = user.getAccount().getEmail();
        dto.firstName = user.getFirstName();
        dto.preposition = user.getPreposition();
        dto.lastName = user.getLastName();
        dto.avatar = "/users/" + user.getUserId() + "/avatar";

        return dto;
    }

}