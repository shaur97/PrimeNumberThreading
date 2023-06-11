public class SimplePrimeCalculation {
    public static int checkPrime(int num) {
        if ((num & 1) == 0)
            return 0;
        for (int i = 3; i <= Math.sqrt(num); i++) {
            if (num % i == 0)
                return 0;
        }
        return 1;
    }
    public static void main (String[] args) {
        int ulimit = 100000000;
        int cnt = 1;
        long total_start_time = System.currentTimeMillis();
        for (int i = 3; i <= ulimit; i++) {
            cnt += checkPrime(i);
        }
        long total_end_time = System.currentTimeMillis();
        System.out.println("Total time taken = " + ((total_end_time - total_start_time) / 1000)
                + " seconds and the count is " + cnt);
    }
}