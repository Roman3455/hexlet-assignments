package exercise;

import java.util.Arrays;

// BEGIN
public class MaxThread extends Thread {

    private final int[] nums;
    private int max;

    public MaxThread(int[] nums) {
        this.nums = nums;
    }

    @Override
    public void run() {

        System.out.println("INFO: Thread " + this.getName() + " started");
        if (nums.length != 0) {
            max = Arrays.stream(nums).max().getAsInt();
        }
        System.out.println("INFO: Thread " + this.getName() + " finished");
    }

    public int getMax() {

        return max;
    }
}
// END
