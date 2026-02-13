package org.example;

public class UserService {
    private final UserRepository userRepository;

    // 通过构造器注入依赖（便于单元测试）
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 根据用户ID获取用户信息，如果用户不存在返回 "User not found"
     */
    public String getUserInfo(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return "User not found";
        }
        return "Name: " + user.getName() + ", Email: " + user.getEmail();
    }

    /**
     * 创建新用户：先检查ID是否已存在，若不存在则保存
     */
    public boolean createUser(User user) {
        if (userRepository.findById(user.getId()) != null) {
            return false; // 用户已存在
        }
        userRepository.save(user);
        return true;
    }

    /**
     * 删除用户：直接调用仓库删除
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}