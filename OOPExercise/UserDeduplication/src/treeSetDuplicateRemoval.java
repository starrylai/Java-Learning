import java.util.TreeSet;
import java.util.Set;

public class treeSetDuplicateRemoval {
    public static void main(String[] args) {
        Set<UserComparable> users = new TreeSet<>();

        UserComparable user1 = new UserComparable(1, "张三");
        UserComparable user2 = new UserComparable(2, "李四");
        UserComparable user3 = new UserComparable(3, "张三");//同名不同ID
        UserComparable user4 = new UserComparable(3, "王五");//不同名同ID
        UserComparable user5 = new UserComparable(4, "赵六");

        System.out.println("添加用户到TreeSet:");
        System.out.println("添加 " + user1 + ": " + users.add(user1));
        System.out.println("添加 " + user2 + ": " + users.add(user2));
        System.out.println("添加 " + user3 + ": " + users.add(user3));
        System.out.println("添加 " + user4 + ": " + users.add(user4));
        System.out.println("添加 " + user5 + ": " + users.add(user5));

        System.out.println("\n最终TreeSet中的用户");
        for (User user : users) {
            System.out.println(user.toString());
        }

        System.out.println("\nTreeSet大小:" + users.size());
    }
}
