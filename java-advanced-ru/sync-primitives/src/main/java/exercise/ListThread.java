package exercise;

// BEGIN
public class ListThread implements Runnable {
    private SafetyList list;

    public ListThread(SafetyList list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            list.add(i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
// END
