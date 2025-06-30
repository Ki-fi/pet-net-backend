package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petnet.com.dtos.PostInputDto;
import petnet.com.models.Post;
import petnet.com.models.PostStatus;
import petnet.com.models.User;
import petnet.com.repositories.PostRepository;
import petnet.com.repositories.UserRepository;
import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public void createPost(PostInputDto dto) {

        if (dto.startDate == null || dto.endDate == null) {
            throw new IllegalArgumentException("Start- en einddatum zijn verplicht.");
        }

        if (dto.endDate.isBefore(dto.startDate)) {
            throw new IllegalArgumentException("Einddatum mag niet voor de startdatum liggen.");
        }

        Post post = new Post();
        post.setTitle(dto.title);
        post.setStartDate(dto.startDate);
        post.setEndDate(dto.endDate);
        post.setRemark(dto.remark);
        post.setPostStatus(PostStatus.ACTIVE);
        post.setCreatedAt(LocalDateTime.now());

        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));

        post.setCreator(user);
        postRepository.save(post);
    }

}
