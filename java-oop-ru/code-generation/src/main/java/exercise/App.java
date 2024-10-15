package exercise;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

// BEGIN
public class App {

    public static void save(Path filepath, Car car) throws IOException {
        Files.writeString(filepath, car.serialize());
    }

    public static Car extract(Path filepath) throws IOException {
        var content = Files.readString(filepath);
        return Car.deserialize(content);
    }
}
// END
