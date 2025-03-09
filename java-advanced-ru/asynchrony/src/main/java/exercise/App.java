package exercise;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.StandardOpenOption;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String path1, String path2, String destination) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Path src1 = Paths.get(path1);
                if (!Files.exists(src1)) {
                    throw new IOException("NoSuchFileException");
                }
                String content1 = Files.readString(src1);

                Path src2 = Paths.get(path2);
                if (!Files.exists(src2)) {
                    throw new IOException("NoSuchFileException");
                }
                String content2 = Files.readString(src2);

                String combined = content1 + content2;

                Path dest = Paths.get(destination);
                Files.writeString(dest, combined, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                return "Files successfully merged into " + destination;
            } catch (IOException e) {
                throw new RuntimeException("Error while processing files: " + e.getMessage());
            }
        }).exceptionally(ex -> {
            System.out.println("Exception occurred: " + ex.getMessage());
            return null;
        });
    }

    public static CompletableFuture<Long> getDirectorySize(String directoryPath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                File directory = new File(directoryPath);
                if (!directory.exists() || !directory.isDirectory()) {
                    throw new IOException("Directory " + directoryPath + " does not exist or is not a directory.");
                }

                long size = 0;
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            size += file.length();
                        }
                    }
                }
                return size;
            } catch (IOException e) {
                throw new RuntimeException("Error while calculating directory size: " + e.getMessage());
            }
        }).exceptionally(ex -> {
            System.err.println("Exception occurred: " + ex.getMessage());
            return null;
        });
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> result = unionFiles(
                "src/main/resources/file1.txt",
                "src/main/resources/file2.txt",
                "src/main/resources/dest.txt"
        );

        result.thenAccept(System.out::println).join();

        CompletableFuture<Long> sizeFuture = getDirectorySize("src/main/resources");
        sizeFuture.thenAccept(size -> System.out.println("Directory size: " + size + " bytes")).join();
        // END
    }
}

