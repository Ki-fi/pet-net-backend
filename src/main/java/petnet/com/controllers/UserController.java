package petnet.com.controllers;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import petnet.com.services.UserService;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}/avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable("id") Long userId, @RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        userService.uploadAvatar(userId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Avatar geupload!");
    }

    @GetMapping("{id}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable("id") Long userId) {
        Resource file = userService.getAvatar(userId);
        String contentType = URLConnection.guessContentTypeFromName(file.getFilename());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }


        try {
            contentType = Files.probeContentType(file.getFile().toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }
}
