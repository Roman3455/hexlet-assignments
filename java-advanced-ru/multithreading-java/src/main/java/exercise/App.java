package exercise;

import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static Map<String, Integer> getMinMax(int[] numbers) {
        MaxThread maxThread = new MaxThread(numbers);
        MinThread minThread = new MinThread(numbers);

        maxThread.start();
        LOGGER.log(Level.INFO, "Thread " + maxThread.getName() + " started");
        minThread.start();
        LOGGER.log(Level.INFO, "Thread " + minThread.getName() + " started");

        try {
            maxThread.join();
            LOGGER.log(Level.INFO, "Thread " + maxThread.getName() + " finished");
            minThread.join();
            LOGGER.log(Level.INFO, "Thread " + minThread.getName() + " finished");
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, "Thread interrupted", e);
        }

        return Map.of("min", minThread.getMin(), "max", maxThread.getMax());
    }

    public static void main(String[] args) {
        int[] numbers = {10, -4, 67, 100, -100, 8};
        System.out.println(App.getMinMax(numbers));
    }
    // END
}
