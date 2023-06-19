import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeCalculationFairThreading {
    static AtomicInteger count = new AtomicInteger(0);
    static AtomicInteger num = new AtomicInteger(2);
    static long time1, time2;
    static long  thread_start_time, thread_end_time;
    private static int threadsCompleted = 0;
    private static final Object lock = new Object();
    static ExecutorService executorService;

    class Task implements Runnable {

        @Override
        public void run() {
            thread_start_time = System.currentTimeMillis();
            while (num.get() <= 100000000) {
                if(count.get() == 5761455) {
                    System.out.println("num ===== " + num.get());
                }
                if(num.get() > 100000000) {
                    System.out.println("exceeded = " + num.get());
                }
                if(checkPrime(num.get()) == 1) {
                    synchronized (Task.class) {
                        count.addAndGet(1);
                        num.addAndGet(1);
                        continue;
                    }
                }
                synchronized (Task.class) {
                    num.addAndGet(1);
                }
                //System.out.println("Total time taken for thread = " + ((thread_end_time - thread_start_time) / 1000) + " seconds");
            }
            synchronized (Task.class) {
                threadsCompleted++;
                thread_end_time = System.currentTimeMillis();
                System.out.println("time taken by thread " + Thread.currentThread() + " = "
                        + (thread_end_time - thread_start_time) / 1000 + " seconds");
                if (threadsCompleted == 5) {
                    time2 = System.currentTimeMillis();
                    System.out.println("Total time taken for thread = " +
                            ((time2 - time1) / 1000) + " seconds" + " and count = " + count + " and threads completed" +
                            " = " + threadsCompleted);
                }
            }
        }
    }
    public void manageThreads(int no_of_concurrent_threads)  {
        executorService = Executors.newFixedThreadPool(no_of_concurrent_threads);
        try {
            for(int i = 0; i < no_of_concurrent_threads; i++) {
                executorService.execute(new Task());
            }
        } finally {
            executorService.shutdown();
        }
    }
    public static int checkPrime(int num1) {
        if (num1 > 2 && (num1 & 1) == 0)
            return 0;

        for (int i = 2; i <= Math.sqrt(num1); i++) {
            if (num1 % i == 0)
                return 0;
        }
        return 1;
    }
    public static void main(String[] args) {
        int no_concurrent_threads = 5;
        int ulimit = 100000000;

        PrimeCalculationFairThreading PrimeCalculationFairThreading = new PrimeCalculationFairThreading();
        time1 = System.currentTimeMillis();
        PrimeCalculationFairThreading.manageThreads(no_concurrent_threads);
    }
}
