package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import petnet.com.models.User;
import petnet.com.repositories.UserRepository;
import petnet.com.utils.ValidateUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void uploadAvatar(Long userId, MultipartFile file) {
        ValidateUpload.validateAvatar(file);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));

        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/avatars/" + filename);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            user.setAvatar("/uploads/avatars/" + filename);
            userRepository.save(user);

        } catch (IOException e) {
            throw new RuntimeException("Fout bij opslaan van de avatar", e);
        }
    }
}
