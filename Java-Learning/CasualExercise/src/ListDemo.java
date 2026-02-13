import java.util.*;

public class ListDemo {
    public static void main(String[] args) {
        // 创建ArrayList
        List<String> arrayList = new ArrayList<>();

        // 添加元素
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Orange");
        arrayList.add(1, "Grape"); // 在指定位置插入

        System.out.println("ArrayList: " + arrayList);

        // 随机访问 - O(1)时间复杂度
        System.out.println("第二个元素: " + arrayList.get(1));

        // 遍历方式
        System.out.println("=== 遍历方式 ===");

        // 1. for循环
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println("索引 " + i + ": " + arrayList.get(i));
        }

        // 2. 增强for循环
        for (String fruit : arrayList) {
            System.out.println("水果: " + fruit);
        }

        // 3. 迭代器
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println("迭代器: " + iterator.next());
        }

        // 常用操作
        arrayList.set(2, "Peach"); // 修改元素
        arrayList.remove("Banana"); // 删除元素
        System.out.println("修改后: " + arrayList);
    }
}
