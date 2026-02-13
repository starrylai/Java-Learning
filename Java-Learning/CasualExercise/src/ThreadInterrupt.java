// 中断状态变化的示例
public class ThreadInterrupt {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            // 初始状态
            System.out.println("1. 初始中断状态: " + Thread.currentThread().isInterrupted());

            // 中断自己
            Thread.currentThread().interrupt();
            System.out.println("2. 中断后状态: " + Thread.currentThread().isInterrupted());

            // 使用 Thread.interrupted() 检查并清除
            boolean cleared = Thread.interrupted();
            System.out.println("3. Thread.interrupted() 返回: " + cleared);
            System.out.println("4. 清除后状态: " + Thread.currentThread().isInterrupted());

            // 再次中断
            Thread.currentThread().interrupt();

            try {
                System.out.println("5. 调用 sleep() 前状态: " + Thread.currentThread().isInterrupted());
                Thread.sleep(1000);  // 会立即抛出 InterruptedException
            } catch (InterruptedException e) {
                System.out.println("6. 捕获异常后状态: " + Thread.currentThread().isInterrupted());
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}