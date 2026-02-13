import java.util.concurrent.atomic.*;

/**
 * 原子类使用示例
 */
public class AtomicExample {

    // 使用AtomicInteger解决原子性问题
    private AtomicInteger atomicCount = new AtomicInteger(0);
    private AtomicReference<String> atomicReference = new AtomicReference<>("initial");
    private AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    /**
     * AtomicInteger常用方法演示
     */
    public void testAtomicInteger() {
        System.out.println("=== AtomicInteger演示 ===");

        // 基础操作
        atomicCount.set(100);
        System.out.println("set(100)后: " + atomicCount.get());

        // 原子递增
        int result = atomicCount.incrementAndGet();  // 相当于 ++i
        System.out.println("incrementAndGet()后: " + result);

        result = atomicCount.getAndIncrement();  // 相当于 i++
        System.out.println("getAndIncrement()后: " + result + ", 当前值: " + atomicCount.get());

        // 原子递减
        atomicCount.decrementAndGet();
        System.out.println("decrementAndGet()后: " + atomicCount.get());

        // 原子加法
        atomicCount.addAndGet(50);
        System.out.println("addAndGet(50)后: " + atomicCount.get());

        // CAS操作
        boolean success = atomicCount.compareAndSet(151, 200);
        System.out.println("CAS(151->200): " + success + ", 当前值: " + atomicCount.get());

        success = atomicCount.compareAndSet(200, 0);
        System.out.println("CAS(200->0): " + success + ", 当前值: " + atomicCount.get());
    }

    /**
     * 解决volatile的原子性问题
     */
    public void solveAtomicityProblem() {
        System.out.println("\n=== 使用AtomicInteger解决原子性问题 ===");

        // 创建10个线程同时递增atomicCount
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicCount.incrementAndGet();  // 原子操作
                }
            });
            threads[i].start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("期望值: 10000, 实际值: " + atomicCount.get());
    }

    /**
     * AtomicReference使用示例
     */
    public void testAtomicReference() {
        System.out.println("\n=== AtomicReference演示 ===");

        // 模拟用户状态更新
        User user = new User("张三", 25);
        AtomicReference<User> userRef = new AtomicReference<>(user);

        // 原子更新
        User newUser = new User("李四", 30);
        boolean updated = userRef.compareAndSet(user, newUser);
        System.out.println("更新用户: " + updated);
        System.out.println("当前用户: " + userRef.get());

        // 复杂更新
        userRef.getAndUpdate(u -> new User(u.getName(), u.getAge() + 1));
        System.out.println("年龄增加后: " + userRef.get());
    }

    /**
     * AtomicBoolean使用示例
     */
    public void testAtomicBoolean() {
        System.out.println("\n=== AtomicBoolean演示 ===");

        // 原子布尔值操作
        System.out.println("初始值: " + atomicBoolean.get());

        // 设置值
        atomicBoolean.set(false);
        System.out.println("set(false)后: " + atomicBoolean.get());

        // 原子比较并设置
        boolean success = atomicBoolean.compareAndSet(false, true);
        System.out.println("CAS(false->true): " + success + ", 当前值: " + atomicBoolean.get());
    }

    /**
     * 高性能计数器示例
     */
    public void testLongAdder() {
        System.out.println("\n=== LongAdder高性能计数器演示 ===");

        // LongAdder在高并发场景下比AtomicLong性能更好
        LongAdder longAdder = new LongAdder();

        // 创建多个线程并发增加
        Thread[] adderThreads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            adderThreads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    longAdder.increment();
                }
            });
            adderThreads[i].start();
        }

        // 等待完成
        for (Thread thread : adderThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("LongAdder最终值: " + longAdder.sum());
    }

    static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + "}";
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }

    public static void main(String[] args) {
        AtomicExample example = new AtomicExample();
        example.testAtomicInteger();
        example.solveAtomicityProblem();
        example.testAtomicReference();
        example.testAtomicBoolean();
        example.testLongAdder();
    }
}