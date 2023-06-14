# PrimeNumberThreading
This project explores multiple ways of finding the number of prime numbers upto 100 million.
<br>
There are 3 ways to do this:
<br>
Single threaded simple calculation to check whether a number is prime or not.
<br>
Batched unfair multithreaded approach by creating batches of 10 million each and assigning to 10 threads
<br>
Fair multithreading where shared atomic variables are updated by multiple threads running concurrently to make sure each thread takes the same amount of time
