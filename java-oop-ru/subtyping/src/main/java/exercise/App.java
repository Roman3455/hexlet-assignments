package exercise;

// BEGIN
public class App {
    public static void swapKeyValue(KeyValueStorage database) {
        database.toMap().forEach((k, v) -> {
            database.unset(k);
            database.set(v, k);
        });
    }
}
// END
