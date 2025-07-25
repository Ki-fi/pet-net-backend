package petnet.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import petnet.com.dtos.PostInputDto;
import petnet.com.dtos.PostOutputDto;
import petnet.com.services.PostService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostInputDto dto, @AuthenticationPrincipal UserDetails userDetails) {
        postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post aangemaakt!");
    }

    @GetMapping
    public ResponseEntity<List<PostOutputDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostOutputDto> getPostById(@PathVariable Long id) {
        PostOutputDto dto = postService.getPostById(id);
        return ResponseEntity.ok(dto);
    }


}
