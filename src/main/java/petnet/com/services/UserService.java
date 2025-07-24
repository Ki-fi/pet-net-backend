package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import petnet.com.exceptions.AvatarNotFoundException;
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
            String filename = "avatar of user_" + userId + extension;
            Path path = Paths.get("uploads/avatars/").resolve(filename);
            Files.createDirectories(path.getParent());

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            user.setAvatar(filename);
            userRepository.save(user);

        } catch (IOException e) {
            throw new RuntimeException("Fout bij opslaan van de avatar", e);
        }
    }

    public Resource getAvatar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(AvatarNotFoundException::new);

        String avatar = user.getAvatar();
        if (avatar == null || avatar.isBlank()) {
            throw new AvatarNotFoundException();
        }

        Path path = Paths.get("uploads/avatars/").resolve(avatar);
        try {
            Resource file = new UrlResource(path.toUri());

            if (!file.exists() || !file.isReadable()) {
                throw new AvatarNotFoundException();
            }
            return file;

        } catch (MalformedURLException e) {
            throw new AvatarNotFoundException();
        }
    }


}
