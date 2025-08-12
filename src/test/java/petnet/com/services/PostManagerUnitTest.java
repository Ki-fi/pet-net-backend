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
import petnet.com.models.*;
import petnet.com.repositories.PostRepository;
import petnet.com.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PostServiceManager postServiceManager;

    @InjectMocks
    PostManager postManager;

    PostInputDto dto;
    User testUser;
    Post post;
    List<Post> posts;

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

        posts = List.of(post);

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
        when(postServiceManager.convertToEntities(anyList(), any())).thenReturn(List.of());

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
        when(postRepository.findAll()).thenReturn(posts);

//      When
        List<PostOutputDto> result = postManager.getAllPosts();

//      Then
        assertEquals(post.getTitle(), result.get(0).title);
        assertEquals(testUser.getUserId(), result.get(0).creator);
        assertEquals(post.getCreatedAt(), result.get(0).createdAt);
        assertEquals(post.getStartDate(), result.get(0).startDate);
        assertEquals(post.getEndDate(), result.get(0).endDate);
        assertEquals(post.getRemark(), result.get(0).remark);

    }

    @Test
    void shouldReturnCorrectResponse() {

//      Given
        when(postRepository.findAll()).thenReturn(List.of(post));

//      When
        List<PostOutputDto> result = postManager.getAllPosts();

//      Then
        ResponseOutputDto mappedResponse = result.get(0).responses.get(0);
        assertEquals(post.getResponses().get(0).getResponseId(), mappedResponse.responseId);
        assertEquals(post.getResponses().get(0).getComment(), mappedResponse.comment);
        assertEquals(post.getResponses().get(0).getCreatedAt(), mappedResponse.createdAt);
        assertEquals(testUser.getUserId(), mappedResponse.userId);
        assertEquals(testUser.getFirstName(), mappedResponse.firstName);
        assertEquals(testUser.getPreposition(), mappedResponse.preposition);
        assertEquals(testUser.getLastName(), mappedResponse.lastName);

    }

    @Test
    void shouldReturnCorrectServices() {

//      Given
        when(postRepository.findAll()).thenReturn(List.of(post));

//      When
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

//      When
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
        System.out.println("User role: " + admin.getUserRole());

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


