package petnet.com.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import petnet.com.PetNetApplication;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PetNetApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PostControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreatePost() throws Exception {

//      Given
        String requestJSON = """
                {
                  "startDate": "2026-01-01",
                  "endDate": "2026-02-02",
                  "title": "Test post",
                  "userId": 1
                }
                """;

//      When
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                        .andExpect(status().isCreated());

//      Then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[3].title").value("Test post"))
                        .andExpect(jsonPath("$[3].startDate").value("2026-01-01"))
                        .andExpect(jsonPath("$[3].creator").value(1));

    }

    @Test
    void shouldReturnPostById() throws Exception {

//      Given
        String requestJSON = """
            {
              "startDate": "2026-01-01",
              "endDate": "2026-02-02",
              "title": "Test post by ID",
              "userId": 1
            }
            """;

//      When
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isCreated())
                .andReturn();

//      Then
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test post by ID"))
                .andExpect(jsonPath("$.creator").value(1));

    }

}