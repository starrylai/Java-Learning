import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerAndConsumer {
    private static final Integer POISON_PILL = -1;

    static class Producer implements Runnable{
        private final BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue){
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 20; i++) {
                    System.out.println("生产者生产：" + i);
                    queue.put(i);
                    Thread.sleep(100);
                }
                queue.put(POISON_PILL);
                queue.put(POISON_PILL);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable{
        private final BlockingQueue<Integer> queue;
        private final String name;

        public Consumer(BlockingQueue<Integer> queue, String name){
            this.queue = queue;
            this.name = name;
        }

        @Override
        public void run() {
            try{
                while(true){
                    Integer item = queue.take();
                    if(item == POISON_PILL){
                        System.out.println(name + "收到毒丸，结束运行");
                        return;
                    }

                    System.out.println(name + "消费" + item);
                    Thread.sleep(100);
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        Thread producer = new Thread(new Producer(queue));
        producer.start();
        Thread consumer1 = new Thread(new Consumer(queue, "Consumer1"));
        Thread consumer2 = new Thread(new Consumer(queue, "Consumer2"));
        consumer1.start();
        consumer2.start();

        try{
            producer.join();
            consumer1.join();
            consumer2.join();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.println("任务完成");
    }
}
