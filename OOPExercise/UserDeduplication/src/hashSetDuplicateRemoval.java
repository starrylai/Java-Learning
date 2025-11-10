import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class hashSetDuplicateRemoval {
    public static void main(String[] args) {
        Set<User> users = new HashSet<User>();

        User user1 = new User(1, "张三");
        User user2 = new User(2, "李四");
        User user3 = new User(3, "张三");//同名不同ID
        User user4 = new User(3, "王五");//不同名同ID
        User user5 = new User(4, "赵六");

        System.out.println("添加用户到HashSet:");
        System.out.println("添加 " + user1 + ": " + users.add(user1));
        System.out.println("添加 " + user2 + ": " + users.add(user2));
        System.out.println("添加 " + user3 + ": " + users.add(user3));
        System.out.println("添加 " + user4 + ": " + users.add(user4)); // 应该返回false
        System.out.println("添加 " + user5 + ": " + users.add(user5));

        System.out.println("\n最终HashSet中的用户");
        for (User user : users) {
            System.out.println(user.toString());
        }

        System.out.println("\nHashSet大小:" + users.size());
    }
}
