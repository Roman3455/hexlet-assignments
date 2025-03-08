package exercise;

import java.util.Arrays;

// BEGIN
public class MinThread extends Thread {

    private final int[] nums;
    private int min;

    public MinThread(int[] nums) {
        this.nums = nums;
    }

    @Override
    public void run() {

        System.out.println("INFO: Thread " + this.getName() + " started");
        if (nums.length != 0) {
            min = Arrays.stream(nums).min().getAsInt();
        }
        System.out.println("INFO: Thread " + this.getName() + " finished");
    }

    public int getMin() {

        return min;
    }
}
// END
