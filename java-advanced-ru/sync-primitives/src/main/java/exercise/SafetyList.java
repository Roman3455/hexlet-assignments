package exercise;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

class SafetyList {
    // BEGIN
    private int[] list;
    private int size;
    private final ReentrantLock lock;

    public SafetyList() {
        this.list = new int[10];
        this.lock = new ReentrantLock();
        this.size = 0;
    }

    public void add(int element) {
        lock.lock();
        try {
            if (size == list.length) {
                list = Arrays.copyOf(list, list.length * 2);
            }
            list[size] = element;
            size++;
        } finally {
            lock.unlock();
        }
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return list[index];
    }

    public int getSize() {
        return size;
    }
    // END
}
