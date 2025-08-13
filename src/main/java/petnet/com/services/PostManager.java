package petnet.com.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import petnet.com.dtos.PostInputDto;
import petnet.com.dtos.PostOutputDto;
import petnet.com.exceptions.PostNotFoundException;
import petnet.com.exceptions.UserNotFoundException;
import petnet.com.mappers.PostMapper;
import petnet.com.models.*;
import petnet.com.repositories.PostRepository;
import petnet.com.repositories.UserRepository;
import java.util.Collections;
import java.util.List;

@Service
public class PostManager {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final PostServiceManager postServiceManager;

    public PostManager(PostRepository postRepository,
                       UserRepository userRepository,
                       PostMapper postMapper,
                       PostServiceManager postServiceManager) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.postServiceManager = postServiceManager;
    }

    public void createPost(PostInputDto dto) {

        if (dto.startDate == null || dto.endDate == null) {
            throw new IllegalArgumentException("Start- en einddatum zijn verplicht.");
        }

        if (dto.endDate.isBefore(dto.startDate)) {
            throw new IllegalArgumentException("Einddatum mag niet voor de startdatum liggen.");
        }

        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));

        List<PostService> services = (dto.services != null && !dto.services.isEmpty())
                ? postServiceManager.convertToEntities(dto.services, null)
                : Collections.emptyList();

        Post post = postMapper.toPostEntity(dto, user, services);

        if (!services.isEmpty()) {
            services.forEach(s -> s.setPost(post));
        }

        postRepository.save(post);
    }

    public List<PostOutputDto> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(postMapper::toPostOutputDto)
                .toList();
    }

    public PostOutputDto getPostById(Long id) {

        return postRepository.findById(id)
                .map(postMapper::toPostOutputDto)
                .orElseThrow(PostNotFoundException::new);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow((UserNotFoundException::new));

        boolean isCreator = post.getCreator().getUserId().equals(userId);
        boolean isAdmin = UserRole.ADMIN.equals(user.getUserRole());

        if (!isCreator && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Je bent niet gemachtigd om deze post te verwijderen");
        }

        postRepository.delete(post);
    }


}
