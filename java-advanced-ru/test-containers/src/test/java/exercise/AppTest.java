package exercise;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@AutoConfigureMockMvc

// BEGIN
@Testcontainers
@Transactional
// END
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    // BEGIN
    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("test-container")
            .withUsername("test-username")
            .withPassword("test-password")
            .withInitScript("init.sql");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void testIndex() throws Exception {
        var request = get("/people");
        MockHttpServletResponse response = mockMvc
                .perform(request)
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
    }

    @Test
    void testShow() throws Exception {
        var request = get("/people/1");
        MockHttpServletResponse response = mockMvc
                .perform(request)
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("John", "Smith");
    }

    @Test
    void testUpdate() throws Exception {
        var request = patch("/people/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Pickle\", \"lastName\": \"Rick\"}");
        MockHttpServletResponse response = mockMvc
                .perform(request)
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        var getRequest = get("/people/2");
        MockHttpServletResponse getResponse = mockMvc
                .perform(getRequest)
                .andReturn()
                .getResponse();
        assertThat(getResponse.getStatus()).isEqualTo(200);
        assertThat(getResponse.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(getResponse.getContentAsString()).contains("Pickle", "Rick");
    }

    @Test
    void testDelete() throws Exception {
        var request = delete("/people/3");
        MockHttpServletResponse response = mockMvc
                .perform(request)
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        var getRequest = get("/people/3");
        MockHttpServletResponse getResponse = mockMvc
                .perform(getRequest)
                .andReturn()
                .getResponse();
        assertThat(getResponse.getContentAsString()).isEmpty();
    }
    // END

    @Test
    void testCreatePerson() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
            .perform(
                post("/people")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Jackson\", \"lastName\": \"Bind\"}")
            )
            .andReturn()
            .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
            .perform(get("/people"))
            .andReturn()
            .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Jackson", "Bind");
    }
}
