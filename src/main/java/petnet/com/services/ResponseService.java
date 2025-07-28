package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petnet.com.dtos.ResponseInputDto;
import petnet.com.models.Post;
import petnet.com.models.Response;
import petnet.com.models.User;
import petnet.com.repositories.PostRepository;
import petnet.com.repositories.ResponseRepository;
import petnet.com.repositories.UserRepository;

import java.time.LocalDateTime;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    public void createResponse(ResponseInputDto dto) {

        Response response = new Response();
        response.setComment(dto.comment);
        response.setCreatedAt(LocalDateTime.now());

        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));

        Post post = postRepository.findById(dto.postId)
                .orElseThrow(() -> new RuntimeException("Post niet gevonden"));

        response.setUserId(user);
        response.setPostId(post);

        responseRepository.save(response);

    }

}
