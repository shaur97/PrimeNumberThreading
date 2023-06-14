import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeCalculationFairThreading {
    static AtomicInteger count = new AtomicInteger(0);
    static AtomicInteger num = new AtomicInteger(2);
    static long time1, time2;
    static long  thread_start_time, thread_end_time;
    private static int threadsCompleted = 0;
    private static final Object lock = new Object();

    public class MyThread implements Runnable {

        @Override
        public void run() {
            thread_start_time = System.currentTimeMillis();
            while (true) {
                int num_int;
                num_int = num.get();
                if(num_int > 100000000)
                    break;

                //synchronized (lock) {
                    checkPrime(num_int);
                    num.addAndGet(1);
                //}

                //System.out.println("Total time taken for thread = " + ((thread_end_time - thread_start_time) / 1000) + " seconds");
            }
            synchronized (lock) {
                threadsCompleted++;
                thread_end_time = System.currentTimeMillis();
                System.out.println("time taken by thread " + Thread.currentThread() + " = "
                        + (thread_end_time - thread_start_time) / 1000 + " seconds");
                if (threadsCompleted == 10) {
                    time2 = System.currentTimeMillis();
                    System.out.println("Total time taken for thread = " +
                            ((time2 - time1) / 1000) + " seconds" + " and count = " + count + " and threads completed" +
                            " = " + threadsCompleted);
                }
            }
        }
    }
    public void createThread(int no_of_concurrent_threads) {
        for(int i = 0; i < no_of_concurrent_threads; i++) {
            Thread t = new Thread(new MyThread());
            t.start();
        }
        //long total_end_time = System.currentTimeMillis();
    }
    public static int checkPrime(int num) {
        if (num > 2 && (num & 1) == 0)
            return 0;

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0)
                return 0;
        }
        count.addAndGet(1);
        return 1;
    }
    public static void main(String[] args) {
        int no_concurrent_threads = 10;
        int ulimit = 100000000;

        PrimeCalculationFairThreading PrimeCalculationFairThreading = new PrimeCalculationFairThreading();
        time1 = System.currentTimeMillis();
        PrimeCalculationFairThreading.createThread(no_concurrent_threads);
    }
}
