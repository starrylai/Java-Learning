// LinkedList - 基于双向链表，插入删除快
import java.util.LinkedList;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();

        // 添加元素
        linkedList.add("First");
        linkedList.add("Second");
        linkedList.addFirst("Head"); // 头部插入
        linkedList.addLast("Tail");  // 尾部插入

        System.out.println("LinkedList: " + linkedList);

        // 头部操作 - O(1)时间复杂度
        linkedList.offerFirst("NewHead"); // 添加头部
        System.out.println("头部元素: " + linkedList.peekFirst());
        System.out.println("删除头部: " + linkedList.pollFirst());

        // 队列操作
        linkedList.offer("QueueEnd"); // 入队
        System.out.println("出队: " + linkedList.poll()); // 出队

        System.out.println("最终结果: " + linkedList);
    }
}