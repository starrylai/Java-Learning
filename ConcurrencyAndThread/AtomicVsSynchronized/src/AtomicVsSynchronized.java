import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVsSynchronized {
    private static final int THREAD_COUNT = 10;
    private static final int INCREMENT_TIMES = 10000;

    public static void main(String[] args) throws InterruptedException{
        System.out.println("======测试AtomicInteger======");
        long atomicTime = testAtomicInteger();

        System.out.println("\n======测试synchronized======");
        long syncTime = testSynchronized();
    }

    private static long testAtomicInteger() throws InterruptedException {
        AtomicInteger Counter = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < THREAD_COUNT; i++){
            executor.submit(() -> {
                try{
                    for(int j = 0; j < INCREMENT_TIMES; j++){
                        Counter.incrementAndGet();
                    }
                }finally{
                    latch.countDown();
                }
            });
        }

        latch.await();
        long endTime = System.currentTimeMillis();
        executor.shutdown();

        System.out.printf("实际结果:%d(期望值:%d)%n", Counter.get(), THREAD_COUNT*INCREMENT_TIMES);
        System.out.printf("耗时:%d ms%n", endTime - startTime);
        return endTime - startTime;
    }

    private static long testSynchronized() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < THREAD_COUNT; i++){
            executor.submit(() -> {
                try{
                    for(int j = 0; j < INCREMENT_TIMES; j++){
                        counter.increment();
                    }
                }finally{
                    latch.countDown();
                }
            });
        }

        latch.await();
        long endTime = System.currentTimeMillis();
        executor.shutdown();

        System.out.printf("实际结果：%d(期望值：%d)%n",counter.getCount(),
                THREAD_COUNT*INCREMENT_TIMES);
        System.out.printf("耗时：%d ms%n", endTime - startTime);

        return endTime - startTime;
    }

    static class SynchronizedCounter{
        private int count = 0;

        public synchronized void increment(){
            count++;
        }

        public void incrementWithBlock(){
            synchronized(this){
                count++;
            }
        }

        public synchronized int getCount(){
            return count;
        }
    }
}
