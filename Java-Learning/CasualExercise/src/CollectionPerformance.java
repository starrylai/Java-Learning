// 集合性能测试和选型建议
import java.util.*;

public class CollectionPerformance {
    public static void main(String[] args) {
        int elementCount = 100000;

        // ArrayList vs LinkedList 性能测试
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // 尾部插入性能
        long start = System.currentTimeMillis();
        for (int i = 0; i < elementCount; i++) {
            arrayList.add(i);
        }
        long arrayListTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < elementCount; i++) {
            linkedList.add(i);
        }
        long linkedListTime = System.currentTimeMillis() - start;

        System.out.println("尾部插入 " + elementCount + " 个元素:");
        System.out.println("ArrayList: " + arrayListTime + "ms");
        System.out.println("LinkedList: " + linkedListTime + "ms");

        // 随机访问性能
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            arrayList.get(i * 100);
        }
        arrayListTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            linkedList.get(i * 100);
        }
        linkedListTime = System.currentTimeMillis() - start;

        System.out.println("\n随机访问 1000 次:");
        System.out.println("ArrayList: " + arrayListTime + "ms");
        System.out.println("LinkedList: " + linkedListTime + "ms");
    }
}