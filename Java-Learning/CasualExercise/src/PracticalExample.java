import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PracticalExample {
    public static void demonstrateThenAccept() {
        CompletableFuture<Integer> future = CompletableFuture.completedFuture(42);

        // thenAccept：消费结果，不产生新值
        CompletableFuture<Void> acceptFuture = future.thenAccept(value -> {
            System.out.println("消费值: " + value);
            // 可以执行副作用操作，如写入日志、更新UI等
        });

        // 返回的是CompletableFuture<Void>，因为不产生新值

        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        demonstrateThenAccept();
    }
}
