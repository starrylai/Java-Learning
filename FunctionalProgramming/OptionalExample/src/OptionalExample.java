import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalExample {
    public static Optional<String> findUserEmail(String userId){
        if(userId.equals("user1")){
            return Optional.of("Alice@example.com");
        }else if(userId.equals("user2")){
            return Optional.of("Emma@example.com");
        }else{
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        List<String> users = Arrays.asList("user1", "user2", "user3");

        for(String user:users){
            String result = findUserEmail(user).map(String::toUpperCase)
                    .orElse("N/A");
            System.out.println("用户名：" + user + "的邮箱：" + result);
        }
    }
}
