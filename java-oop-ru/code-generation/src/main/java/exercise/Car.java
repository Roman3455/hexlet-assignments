package exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Value;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

// BEGIN
@Value
// END
class Car {
    int id;
    String brand;
    String model;
    String color;
    User owner;

    // BEGIN
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String serialize() throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(this);
    }

    public static Car deserialize(String jsonRepresentation) throws IOException {
        return OBJECT_MAPPER.readValue(jsonRepresentation, Car.class);
    }
    // END
}
