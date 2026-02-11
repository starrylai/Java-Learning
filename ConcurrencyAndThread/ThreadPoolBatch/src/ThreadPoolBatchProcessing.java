import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolBatchProcessing {
    public static void main(String[] args) {
        //设置线程池
        int threadPoolSize = 10;
        int taskCount = 100;
        long simulatedDelay = 50;

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        long startTime = System.currentTimeMillis();//时间戳
        List<Future<Long>> futures = new ArrayList<>();

        //提交任务
        for(int i = 0; i < taskCount; i++){
            final int taskId = i;
            Callable<Long> task = () -> {
                Thread.sleep(simulatedDelay);
                return System.currentTimeMillis();
            };
            Future<Long> future = executor.submit(task);
            futures.add(future);
        }

        List<Long> results = new ArrayList<>();
        for(Future<Long> future : futures){
            try{
                Long result = future.get();
                results.add(result);
            }catch(InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        executor.shutdown();
        long totalTime = endTime - startTime;
        double averageTime = (double) totalTime /taskCount;

        System.out.println("任务总数："+taskCount);
        System.out.println("线程池大小："+threadPoolSize);
        System.out.println("单个任务模拟耗时："+simulatedDelay+"ms");
        System.out.println("总耗时："+ totalTime +"ms");
        System.out.println("平均耗时:"+averageTime+"ms");
        System.out.println("完成任务数："+results.size());

        long theoMinTime = (long)Math.ceil((double)taskCount/threadPoolSize)*simulatedDelay;
        System.out.println("理论最小耗时："+theoMinTime+"ms");
        System.out.println("实际效率"+theoMinTime*100.0/ totalTime +"%");
    }
}
