package petnet.com.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import petnet.com.dtos.ProfileOutputDto;
import petnet.com.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/avatar")
    public ResponseEntity<?> uploadAvatar(@PathVariable("id") Long userId, @RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) {
        userService.uploadAvatar(userId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Avatar geupload!");
    }

    @GetMapping("{id}/avatar")
    public ResponseEntity<Resource> getAvatar(@PathVariable("id") Long userId) {
        Resource file = userService.getAvatar(userId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .body(file);
    }

    @GetMapping("{id}/profile")
    public ResponseEntity<ProfileOutputDto> getProfile(@PathVariable("id") Long userId, @AuthenticationPrincipal UserDetails userDetails) {
        ProfileOutputDto dto = userService.getProfile(userId);
        return ResponseEntity.ok(dto);
    }

}
