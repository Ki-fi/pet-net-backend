package petnet.com.services;

import org.junit.jupiter.api.Test;
import petnet.com.dtos.PostServiceInputDto;
import petnet.com.models.Post;
import petnet.com.models.PostService;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceManagerTest {

    PostServiceManager manager = new PostServiceManager();

    @Test
    void shouldConvertToEntities() {

        // Given
        Post post = new Post();
        PostServiceInputDto dto = new PostServiceInputDto();
        dto.title = "Harry voeren";
        dto.description = "Blikje tonijn";

        List<PostServiceInputDto> dtoList = List.of(dto);

        // When
        List<PostService> result = manager.convertToEntities(dtoList, post);

        // Then
        assertEquals(1, result.size());
        assertEquals("Harry voeren", result.get(0).getTitle());
        assertEquals("Blikje tonijn", result.get(0).getDescription());
        assertEquals(post, result.get(0).getPost());
    }

    @Test
    void shouldReturnEmptyListWhenListIsNull() {

        // When
        List<PostService> result = manager.convertToEntities(null, new Post());

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenListIsEmpty() {

        // When
        List<PostService> result = manager.convertToEntities(List.of(), new Post());

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}