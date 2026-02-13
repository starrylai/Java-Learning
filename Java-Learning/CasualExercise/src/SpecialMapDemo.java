// LinkedHashMap和TreeMap演示
import java.util.*;

public class SpecialMapDemo {
    public static void main(String[] args) {
        // LinkedHashMap - 保持插入顺序
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("Orange", 3);
        linkedHashMap.put("Apple", 5);
        linkedHashMap.put("Banana", 2);
        System.out.println("LinkedHashMap: " + linkedHashMap);

        // TreeMap - 按键排序
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Orange", 3);
        treeMap.put("Apple", 5);
        treeMap.put("Banana", 2);
        System.out.println("TreeMap: " + treeMap); // 按键字母顺序排序

        // LRU缓存示例
        Map<String, Integer> lruCache = new LinkedHashMap<String, Integer>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return size() > 3; // 最大容量3个元素
            }
        };

        lruCache.put("A", 1);
        lruCache.put("B", 2);
        lruCache.put("C", 3);
        lruCache.get("A"); // 访问A，使其成为最近使用的
        lruCache.put("D", 4); // 添加D，会淘汰最久未使用的B

        System.out.println("LRU缓存: " + lruCache);
    }
}