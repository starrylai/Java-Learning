public class WhyInterruptWorks {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 核心原理：为什么能中断？ ===\n");

        // 创建一个锁对象，用于模拟阻塞
        Object lock = new Object();

        Thread blockedThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("阻塞线程: 开始等待（模拟阻塞）");
                    lock.wait();  // 这会阻塞线程
                    System.out.println("阻塞线程: 等待结束");
                } catch (InterruptedException e) {
                    System.out.println("阻塞线程: 在wait()中被中断");
                }
            }
        }, "阻塞线程");

        // 启动阻塞线程
        blockedThread.start();

        // 确保阻塞线程进入wait状态
        Thread.sleep(100);

        // 关键：检查阻塞线程的状态
        System.out.println("\n阻塞线程状态: " + blockedThread.getState());
        System.out.println("阻塞线程中断状态: " + blockedThread.isInterrupted());

        // 中断阻塞线程
        System.out.println("\n主线程: 中断阻塞线程");
        blockedThread.interrupt();

        // 等待一小段时间，让中断生效
        Thread.sleep(100);

        System.out.println("中断后阻塞线程状态: " + blockedThread.getState());
        System.out.println("中断后中断状态: " + blockedThread.isInterrupted());

        blockedThread.join();
        System.out.println("\n程序结束");
    }
}