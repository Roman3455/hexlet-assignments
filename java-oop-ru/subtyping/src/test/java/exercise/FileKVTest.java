package exercise;


// BEGIN
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
// END


class FileKVTest {

    private static Path filepath = Paths.get("src/test/resources/file").toAbsolutePath().normalize();

    @BeforeEach
    public void beforeEach() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(new HashMap<String, String>());
        Files.writeString(filepath, content, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // BEGIN
    private FileKV storage;

    @BeforeEach
    public void setUp() {
        storage = new FileKV(filepath.toString(), Map.of("key", "value"));
    }

//    @AfterEach
//    public void tearDown() {
//        File file = new File(filepath.toString());
//        if (file.exists()) {
//            file.delete();
//        }
//    }

    @Test
    public void testGetExistingKey() {
        assertEquals("value", storage.get("key", "default"));
    }

    @Test
    public void testGetNonExistingKey() {
        assertEquals("default", storage.get("non_existing_key", "default"));
    }

    @Test
    public void testSetNewKey() {
        storage.set("newKey", "newValue");
        assertEquals("newValue", storage.get("newKey", "default"));
    }

    @Test
    public void testUnsetKey() {
        storage.unset("key");
        assertEquals("default", storage.get("key", "default"));
    }
    // END
}
