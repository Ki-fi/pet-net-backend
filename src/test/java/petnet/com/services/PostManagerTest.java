package petnet.com.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import petnet.com.dtos.PostInputDto;
import petnet.com.dtos.PostOutputDto;
import petnet.com.models.Post;
import petnet.com.models.PostStatus;
import petnet.com.models.User;
import petnet.com.models.UserRole;
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
class PostManagerTest {

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

        dto = new PostInputDto();
        dto.title = "Oppas voor Harry";
        dto.startDate = LocalDate.now();
        dto.endDate = LocalDate.now().plusDays(1);
        dto.remark = "Harry houdt van kaas";
        dto.userId = testUser.getUserId();
        dto.services = List.of();

        post = new Post();
        post.setTitle(dto.title);
        post.setStartDate(dto.startDate);
        post.setEndDate(dto.endDate);
        post.setRemark(dto.remark);
        post.setCreator(testUser);
        post.setCreatedAt(LocalDateTime.now());
        post.setPostStatus(PostStatus.ACTIVE);
        post.setServices(List.of());
        post.setResponses(List.of());

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

    }


}


//      Given

//      When

//      Then