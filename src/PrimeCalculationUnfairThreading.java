import java.util.concurrent.atomic.AtomicInteger;

public class PrimeCalculationUnfairThreading {
    static AtomicInteger count = new AtomicInteger(0);
    static long time1, time2;
    private static int threadsCompleted = 0;

    public class MyThread implements Runnable {
        int batch_lower_limit;
        int batch_upper_limit;

        public MyThread(int batch_lower_limit, int batch_upper_limit) {
            this.batch_lower_limit = batch_lower_limit;
            this.batch_upper_limit = batch_upper_limit;
        }
        @Override
        public void run() {
            long thread_start_time = System.currentTimeMillis();
            for(int i = batch_lower_limit; i <= batch_upper_limit; i++) {
                checkPrime(i);
            }
            long thread_end_time = System.currentTimeMillis();
            //System.out.println("Total time taken for thread = " + ((thread_end_time - thread_start_time) / 1000) + " seconds");
            synchronized (MyThread.class) {
                threadsCompleted++;
                if(threadsCompleted == 10) {
                    time2 = System.currentTimeMillis();
                    System.out.println("Total time taken for thread = " +
                            ((thread_end_time - thread_start_time) / 1000) + " seconds" + " and count = " + count);
                }
            }
        }
    }
    public void createThread(int ulimit, int no_of_concurrent_threads) {
        int batch = 0;
        //long total_start_time = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            int batch_lower_limit = batch;
            int batch_upper_limit = batch_lower_limit + (ulimit / no_of_concurrent_threads);
            Thread t = new Thread(new MyThread(batch_lower_limit, batch_upper_limit));
            batch = batch_upper_limit;
            t.start();
        }
        //long total_end_time = System.currentTimeMillis();
    }
    public static int checkPrime(int num) {
        if ((num & 1) == 0)
            return 0;

        for (int i = 3; i <= Math.sqrt(num); i++) {
            if (num % i == 0)
                return 0;
        }
        count.addAndGet(1);
        return 1;
    }
    public static void main(String[] args) {
        int no_concurrent_threads = 10;
        int ulimit = 100000000;

        PrimeCalculationUnfairThreading primeCalculationUnfairThreading = new PrimeCalculationUnfairThreading();
        time1 = System.currentTimeMillis();
        primeCalculationUnfairThreading.createThread(ulimit, no_concurrent_threads);
    }
}
