package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import petnet.com.dtos.ProfileOutputDto;
import petnet.com.exceptions.UserNotFoundException;
import petnet.com.models.User;
import petnet.com.repositories.UserRepository;
import petnet.com.utils.ValidateUpload;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UserService {

    private final UserRepository userRepository;
    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void uploadAvatar(Long userId, MultipartFile file) {
        ValidateUpload.validateAvatar(file);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));

        try {
            String extension = getExtension(file.getOriginalFilename());
            String filename = "avatar_of_user_" + userId + extension;
            Path path = Paths.get("uploads/avatars/").resolve(filename);
            Files.createDirectories(path.getParent());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            user.setAvatar(filename);
            userRepository.save(user);

        } catch (IOException e) {
            throw new RuntimeException("Fout bij opslaan van de avatar", e);
        }
    }

    private ProfileOutputDto convertToProfileOutputDto(User user) {
        ProfileOutputDto dto = new ProfileOutputDto();

        dto.email = user.getAccount().getEmail();
        dto.firstName = user.getFirstName();
        dto.preposition = user.getPreposition();
        dto.lastName = user.getLastName();
        dto.avatar = "/users/" + user.getUserId() + "/avatar";

        return dto;
    }

    public ProfileOutputDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return convertToProfileOutputDto(user);
    }

    public Resource getAvatar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        String file = user.getAvatar();
        if (file == null || file.isBlank()) {
            return null;
        }

        Path path = Paths.get("uploads/avatars/").resolve(file);

        try {
            if (!Files.exists(path)) {
                return null;
            }
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
