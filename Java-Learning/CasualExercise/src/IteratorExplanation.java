//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
import java.util.*;
    public class IteratorExplanation {
        public static void main(String[] args) {
            List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));

            // 获取迭代器
            Iterator<String> iterator = list.iterator();

            // 迭代器内部维护了当前遍历的位置
            while (iterator.hasNext()) {
                String item = iterator.next();
                System.out.println("当前元素: " + item);

                if ("B".equals(item)) {
                    iterator.remove();  // 迭代器知道如何安全删除当前元素
                    System.out.println("删除B后的列表: " + list);
                }
            }
        }
    }