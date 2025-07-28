package petnet.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petnet.com.dtos.PostInputDto;
import petnet.com.dtos.PostOutputDto;
import petnet.com.dtos.ResponseOutputDto;
import petnet.com.exceptions.PostNotFoundException;
import petnet.com.models.Post;
import petnet.com.models.PostStatus;
import petnet.com.models.User;
import petnet.com.repositories.PostRepository;
import petnet.com.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<PostOutputDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts
                .stream()
                .map(this::convertToOutputDto)
                .toList();
    }

    private PostOutputDto convertToOutputDto(Post post) {
        PostOutputDto dto = new PostOutputDto();

        User creator = post.getCreator();

        dto.firstName = creator.getFirstName();
        dto.preposition = creator.getPreposition();
        dto.lastName = creator.getLastName();
        dto.avatar = "/users/" + creator.getUserId() + "/avatar";

        dto.postId = post.getPostId();
        dto.creator = creator.getUserId();
        dto.createdAt = post.getCreatedAt();
        dto.startDate = post.getStartDate();
        dto.endDate = post.getEndDate();
        dto.title = post.getTitle();
        dto.remark = post.getRemark();
        dto.postStatus = post.getPostStatus();

        dto.responses = post.getResponses().stream().map(response -> {
            User user = response.getUserId();

            ResponseOutputDto responseDto = new ResponseOutputDto();
            responseDto.comment = response.getComment();
            responseDto.createdAt = response.getCreatedAt();
            responseDto.userId = user.getUserId();
            responseDto.firstName = user.getFirstName();
            responseDto.preposition = user.getPreposition();
            responseDto.lastName = user.getLastName();
            responseDto.avatar = user.getAvatar();

            return responseDto;
        }).collect(Collectors.toList());

        return dto;
    }

    public PostOutputDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return convertToOutputDto(post);
    }


}
