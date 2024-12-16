package exercise;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import exercise.repository.PostRepository;
import exercise.model.Post;
import org.apache.commons.codec.digest.DigestUtils;

class AppTest {

    private static Javalin app;
    private static String baseUrl;
    private static Post existingPost;

    @BeforeEach
    public void clear() {
        PostRepository.clear();
        existingPost = new Post("test name", "test body 1");
        PostRepository.save(existingPost);
        app = App.getApp();
    }

    @Test
    void testRootPage() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            assertThat(client.get("/").code()).isEqualTo(200);
        });
    }

    @Test
    void testListPosts() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            assertThat(client.get("/posts").code()).isEqualTo(200);
        });
    }

    @Test
    void testBuildPost() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            assertThat(client.get("/posts/build").code()).isEqualTo(200);
        });
    }

    @Test
    void testCreatePost() throws Exception {
        String body = "name=test name 2&body=test body 2";

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/posts", body);
            assertThat(response.code()).isEqualTo(200);
        });

        var post = PostRepository.findByName("test name 2").orElse(null);

        assertThat(post).isNotNull();
        assertThat(post.getName()).isEqualTo("test name 2");
    }

    @Test
    void testShowPost() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/posts/" + existingPost.getId());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains(existingPost.getName());
        });
    }

    @Test
    void testCreatePostNegative() throws Exception {

        String body = "name=test name&body=test";

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/posts", body);
            assertThat(response.code()).isEqualTo(422);
        });
    }

    @Test
    void testEditPost() throws Exception {

        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/posts/" + existingPost.getId() + "/edit");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testUpdatePost() throws Exception {
        String body = "name=new name&body=test content";

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/posts/" + existingPost.getId(), body);
            assertThat(response.code()).isEqualTo(200);

            var actualPost = PostRepository.find(existingPost.getId())
                .orElse(null);
            assertThat(actualPost).isNotNull();
            assertThat(actualPost.getName()).isEqualTo("new name");
            assertThat(actualPost.getBody()).isEqualTo("test content");
        });
    }

    @Test
    void testUpdatePostNegative() throws Exception {

        String body = "name=q&body=test content";

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/posts/" + existingPost.getId(), body);
            assertThat(response.code()).isEqualTo(422);
            assertThat(response.body().string()).contains("q", "test content");

            var actualPost = PostRepository.find(existingPost.getId())
                .orElse(null);
            assertThat(actualPost).isNotNull();
            assertThat(actualPost.getName()).isEqualTo(existingPost.getName());
            assertThat(actualPost.getBody()).isEqualTo(existingPost.getBody());
        });
    }

    @Test
    void testPostNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/posts/999/edit");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    void testDigestHeader1() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            var body = response.body().string();
            var expectedDigest = DigestUtils.sha256Hex(body);

            assertThat(response.code()).isEqualTo(200);
            assertThat(body).isNotNull();

            var actualDigest = response.header("X-Response-Digest");
            assertThat(actualDigest).isNotNull()
                .as("Expect X-Response-Digest header exists");
            assertThat(actualDigest).isEqualTo(expectedDigest);
        });
    }

    @Test
    void testDigestHeader2() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/posts");
            var body = response.body().string();
            var expectedDigest = DigestUtils.sha256Hex(body);

            assertThat(response.code()).isEqualTo(200);
            assertThat(body).isNotNull();

            var actualDigest = response.header("X-Response-Digest");
            assertThat(actualDigest).isNotNull()
                .as("Expect X-Response-Digest header exists");
                assertThat(actualDigest).isEqualTo(expectedDigest);
        });
    }
}
