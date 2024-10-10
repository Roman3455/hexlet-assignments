package exercise;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

// BEGIN
public class FileKV implements KeyValueStorage {
    private String filepath;
    private Map<String, String> database;

    public FileKV(String filepath, Map<String, String> inputData) {
        this.filepath = filepath;
        this.database = new HashMap<>(inputData);
        saveToFile();
    }

    @Override
    public void set(String key, String value) {
        database.put(key, value);
        saveToFile();
    }

    @Override
    public void unset(String key) {
        database.remove(key);
        saveToFile();
    }

    @Override
    public String get(String key, String defaultValue) {
        return database.getOrDefault(key, defaultValue);
    }

    @Override
    public Map<String, String> toMap() {
        return new HashMap<>(database);
    }

    private void saveToFile() {
        String json = Utils.serialize(database);
        Utils.writeFile(filepath, json);
    }

    private void loadFromFile() {
        File file = new File(filepath);
        if (file.exists()) {
            String content = Utils.readFile(filepath);
            database = Utils.deserialize(content);
        }
    }
}
// END
