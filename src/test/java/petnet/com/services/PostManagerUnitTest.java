package petnet.com.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import petnet.com.dtos.PostInputDto;
import petnet.com.dtos.PostOutputDto;
import petnet.com.dtos.PostServiceOutputDto;
import petnet.com.dtos.ResponseOutputDto;
import petnet.com.exceptions.PostNotFoundException;
import petnet.com.mappers.PostMapper;
import petnet.com.models.*;
import petnet.com.repositories.PostRepository;
import petnet.com.repositories.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostManagerUnitTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostServiceManager postServiceManager;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostManager postManager;
    private PostInputDto dto;
    private User testUser;
    private Post post;
    private List<Post> posts;

    @BeforeEach
    void setUp() {

        testUser = new User();
        testUser.setUserId(1L);
        testUser.setFirstName("Tester");
        testUser.setPreposition("Mc");
        testUser.setLastName("Testface");
        testUser.setRole(UserRole.USER);

        dto = new PostInputDto();
        dto.title = "Oppas voor Harry";
        dto.startDate = LocalDate.now();
        dto.endDate = LocalDate.now().plusDays(1);
        dto.remark = "Harry houdt van kaas";
        dto.userId = testUser.getUserId();
        dto.services = List.of();

            Response response = new Response();
            response.setResponseId(100L);
            response.setComment("Ik kan op Harry passen");
            response.setCreatedAt(LocalDateTime.of(2025, 8, 11, 12, 0));
            response.setUserId(testUser);

            PostService service = new PostService();
            service.setServiceId(100L);
            service.setTitle("Harry voeren");
            service.setDescription("Blikje tonijn");

        post = new Post();
        post.setPostId(1L);
        post.setTitle(dto.title);
        post.setStartDate(dto.startDate);
        post.setEndDate(dto.endDate);
        post.setRemark(dto.remark);
        post.setCreator(testUser);
        post.setCreatedAt(LocalDateTime.now());
        post.setPostStatus(PostStatus.ACTIVE);
        post.setServices(List.of(service));
        post.setResponses(List.of(response));

        posts = new ArrayList<>();
        posts.add(post);

    }

    @Test
    void shouldThrowWhenStartOrEndDateNotGiven() {

//      Given
        PostInputDto testDto = new PostInputDto();
        testDto.title = dto.title;
        testDto.startDate = null;
        testDto.endDate = null;
        testDto.remark = dto.remark;
        testDto.userId = dto.userId;
        testDto.services = dto.services;

//      When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postManager.createPost(testDto);
        });

//      Then
        assertEquals("Start- en einddatum zijn verplicht.", exception.getMessage());

    }

    @Test
    void shouldThrowWhenEndDateIsBeforeStartDate() {

//      Given
        PostInputDto testDto = new PostInputDto();
        testDto.title = dto.title;
        testDto.startDate = LocalDate.now();
        testDto.endDate = LocalDate.now().minusDays(1);
        testDto.remark = dto.remark;
        testDto.userId = dto.userId;
        testDto.services = dto.services;

//      When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            postManager.createPost(testDto);
        });

//      Then
        assertEquals("Einddatum mag niet voor de startdatum liggen.", exception.getMessage());

    }

    @Test
    void shouldThrowWhenUserNotFount() {

//      Given
        PostInputDto testDto = new PostInputDto();
        testDto.title = dto.title;
        testDto.startDate = dto.startDate;
        testDto.endDate = dto.endDate;
        testDto.remark = dto.remark;
        testDto.userId = 4L;
        testDto.services = dto.services;

//      When
        Exception exception = assertThrows(RuntimeException.class, () -> {
            postManager.createPost(testDto);
        });

//      Then
        assertEquals("Gebruiker niet gevonden", exception.getMessage());

    }

    @Test
    void userShouldBecomePostCreator() {

//      Given
        when(userRepository.findById(dto.userId)).thenReturn(Optional.of(testUser));
        when(postMapper.toPostEntity(any(PostInputDto.class), any(User.class), anyList()))
                .thenAnswer(invocation -> {
                    PostInputDto dtoArg = invocation.getArgument(0);
                    User userArg = invocation.getArgument(1);
                    List<PostService> servicesArg = invocation.getArgument(2);

                    Post post = new Post();
                    post.setTitle(dtoArg.title);
                    post.setStartDate(dtoArg.startDate);
                    post.setEndDate(dtoArg.endDate);
                    post.setRemark(dtoArg.remark);
                    post.setCreator(userArg);
                    post.setServices(servicesArg);
                    post.setPostStatus(PostStatus.ACTIVE);
                    post.setCreatedAt(LocalDateTime.now());
                    return post;
                });

//      When
        postManager.createPost(dto);

//      Then
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postCaptor.capture());

        Post savedPost = postCaptor.getValue();
        assertEquals(dto.userId, savedPost.getCreator().getUserId());

    }

    @Test
    void shouldReturnCorrectPost() {

//      Given
        PostOutputDto dto = new PostOutputDto();
        dto.title = post.getTitle();
        dto.creator = post.getCreator().getUserId();
        dto.createdAt = post.getCreatedAt();
        dto.startDate = post.getStartDate();
        dto.endDate = post.getEndDate();

        when(postMapper.toPostOutputDto(post)).thenReturn(dto);
        when(postRepository.findAll()).thenReturn(posts);

//      When
        List<PostOutputDto> result = postManager.getAllPosts();

//      Then
        assertEquals(post.getTitle(), result.get(0).title);
        assertEquals(testUser.getUserId(), result.get(0).creator);
        assertEquals(post.getCreatedAt(), result.get(0).createdAt);
        assertEquals(post.getStartDate(), result.get(0).startDate);
        assertEquals(post.getEndDate(), result.get(0).endDate);

    }

    @Test
    void shouldReturnCorrectResponse() {

//      Given
        Response response = post.getResponses().get(0);

        ResponseOutputDto responseDto = new ResponseOutputDto();
        responseDto.responseId = response.getResponseId();
        responseDto.comment = response.getComment();
        responseDto.createdAt = response.getCreatedAt();
        responseDto.userId = response.getUserId().getUserId();

        PostOutputDto postDto = new PostOutputDto();
        postDto.responses = List.of(responseDto);

//      When
        when(postMapper.toPostOutputDto(post)).thenReturn(postDto);
        when(postRepository.findAll()).thenReturn(posts);
        List<PostOutputDto> result = postManager.getAllPosts();

//      Then
        assertEquals(1, result.get(0).responses.size());
        assertEquals(response.getResponseId(), result.get(0).responses.get(0).responseId);
        assertEquals(response.getComment(), result.get(0).responses.get(0).comment);
        assertEquals(response.getCreatedAt(), result.get(0).responses.get(0).createdAt);
        assertEquals(testUser.getUserId(), result.get(0).responses.get(0).userId);

    }

    @Test
    void shouldReturnCorrectServices() {

//      Given
        PostService service = post.getServices().get(0);

        PostServiceOutputDto serviceDto = new PostServiceOutputDto();
        serviceDto.serviceId = service.getServiceId();
        serviceDto.title = service.getTitle();
        serviceDto.description = service.getDescription();

        PostOutputDto postDto = new PostOutputDto();
        postDto.services = List.of(serviceDto);

//      When
        when(postMapper.toPostOutputDto(post)).thenReturn(postDto);
        when(postRepository.findAll()).thenReturn(posts);
        List<PostOutputDto> result = postManager.getAllPosts();

//      Then
        PostServiceOutputDto mappedService = result.get(0).services.get(0);
        assertEquals(post.getServices().get(0).getServiceId(), mappedService.serviceId);
        assertEquals(post.getServices().get(0).getTitle(), mappedService.title);
        assertEquals(post.getServices().get(0).getDescription(), mappedService.description);

    }

    @Test
    void shouldThrowWhenPostNotFound() {

//      Given
        when(postRepository.findById(4L)).thenReturn(Optional.empty());

//      When
        Exception exception = assertThrows(PostNotFoundException.class, () -> {
            postManager.getPostById(4L);
        });

//      Then
        assertEquals("Deze post bestaat niet.", exception.getMessage());

    }

    @Test
    void shouldReturnPostMatchingId() {

//      Given
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostOutputDto postDto = new PostOutputDto();
        postDto.title = post.getTitle();
        postDto.creator = post.getCreator().getUserId();
        postDto.createdAt = post.getCreatedAt();
        postDto.startDate = post.getStartDate();
        postDto.endDate = post.getEndDate();
        postDto.remark = post.getRemark();

//      When
        when(postMapper.toPostOutputDto(post)).thenReturn(postDto);
        PostOutputDto result = postManager.getPostById(1L);

//      Then
        assertEquals(testUser.getUserId(), result.creator);
        assertEquals(dto.title, result.title);

    }

    @Test
    void shouldAllowDeleteWhenCreator() {

//      Given
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(testUser.getUserId())).thenReturn(Optional.of(testUser));

//      When
        assertDoesNotThrow(() -> postManager.deletePost(1L, testUser.getUserId()));

//      Then
        assertEquals(testUser.getUserId(), post.getCreator().getUserId());
        verify(postRepository).delete(post);
    }

    @Test
    void shouldAllowDeleteWhenAdmin() {

//      Given
        User admin = new User();
        admin.setUserId(2L);
        admin.setRole(UserRole.ADMIN);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(admin.getUserId())).thenReturn(Optional.of(admin));

//      When
        assertDoesNotThrow(() -> postManager.deletePost(1L, admin.getUserId()));

//      Then
        verify(postRepository).delete(post);
    }


    @Test
    void shouldThrowWhenNotAuthorized() {

//      Given
        User unauthorized = new User();
        unauthorized.setUserId(3L);
        unauthorized.setRole(UserRole.USER);

        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        when(userRepository.findById(unauthorized.getUserId())).thenReturn(Optional.of(unauthorized));

//      When
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            postManager.deletePost(post.getPostId(), unauthorized.getUserId());
        });

//      Then
        assertEquals("Je bent niet gemachtigd om deze post te verwijderen", exception.getReason());

    }

}


