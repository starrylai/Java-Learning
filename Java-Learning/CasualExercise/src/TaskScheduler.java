import java.util.concurrent.locks.*;
import java.util.*;

public class TaskScheduler {
    private final ReentrantLock lock = new ReentrantLock();

    // 三个优先级队列的条件
    private final Condition highPriorityReady = lock.newCondition();
    private final Condition mediumPriorityReady = lock.newCondition();
    private final Condition lowPriorityReady = lock.newCondition();

    // 任务队列
    private final Queue<Runnable> highPriorityQueue = new LinkedList<>();
    private final Queue<Runnable> mediumPriorityQueue = new LinkedList<>();
    private final Queue<Runnable> lowPriorityQueue = new LinkedList<>();

    private volatile boolean shutdown = false;

    // 工作线程
    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!shutdown) {
                try {
                    Runnable task = takeTask();
                    if (task != null) {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    // 响应中断
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        private Runnable takeTask() throws InterruptedException {
            lock.lock();
            try {
                // 优先级策略：先检查高优先级，再中优先级，最后低优先级
                while (!shutdown &&
                        highPriorityQueue.isEmpty() &&
                        mediumPriorityQueue.isEmpty() &&
                        lowPriorityQueue.isEmpty()) {
                    // 所有队列都空，等待任何任务
                    System.out.println(Thread.currentThread().getName() + " 等待任务...");
                    lowPriorityReady.await();  // 用低优先级队列作为通用等待
                }

                if (shutdown) return null;

                // 按优先级获取任务
                if (!highPriorityQueue.isEmpty()) {
                    return highPriorityQueue.poll();
                } else if (!mediumPriorityQueue.isEmpty()) {
                    return mediumPriorityQueue.poll();
                } else {
                    return lowPriorityQueue.poll();
                }

            } finally {
                lock.unlock();
            }
        }
    }

    // 提交任务
    public void submit(Runnable task, Priority priority) {
        lock.lock();
        try {
            switch (priority) {
                case HIGH:
                    highPriorityQueue.offer(task);
                    highPriorityReady.signal();  // 唤醒等待高优先级任务的线程
                    break;
                case MEDIUM:
                    mediumPriorityQueue.offer(task);
                    mediumPriorityReady.signal();  // 唤醒等待中优先级任务的线程
                    break;
                case LOW:
                    lowPriorityQueue.offer(task);
                    lowPriorityReady.signal();  // 唤醒等待低优先级任务的线程
                    break;
            }
            System.out.println("提交" + priority + "优先级任务，当前队列大小: "
                    + "高:" + highPriorityQueue.size()
                    + " 中:" + mediumPriorityQueue.size()
                    + " 低:" + lowPriorityQueue.size());
        } finally {
            lock.unlock();
        }
    }

    // 启动调度器
    public void start(int workerCount) {
        for (int i = 0; i < workerCount; i++) {
            new WorkerThread().start();
        }
    }

    // 关闭调度器
    public void shutdown() {
        shutdown = true;
        lock.lock();
        try {
            // 唤醒所有等待的线程
            highPriorityReady.signalAll();
            mediumPriorityReady.signalAll();
            lowPriorityReady.signalAll();
        } finally {
            lock.unlock();
        }
    }

    enum Priority {
        HIGH, MEDIUM, LOW
    }

    public static void main(String[] args) throws InterruptedException {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.start(3);  // 启动3个工作线程

        // 提交不同优先级的任务
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            scheduler.submit(() -> {
                System.out.println("执行任务" + taskId + " 优先级: HIGH");
            }, Priority.HIGH);

            scheduler.submit(() -> {
                System.out.println("执行任务" + taskId + " 优先级: MEDIUM");
            }, Priority.MEDIUM);

            scheduler.submit(() -> {
                System.out.println("执行任务" + taskId + " 优先级: LOW");
            }, Priority.LOW);

            Thread.sleep(500);
        }

        Thread.sleep(5000);
        scheduler.shutdown();
    }
}