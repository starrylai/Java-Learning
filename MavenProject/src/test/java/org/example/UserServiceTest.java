package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // 启用 Mockito 对 JUnit 5 的支持
class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // 创建 Mock 对象

    @InjectMocks
    private UserService userService;        // 将 Mock 注入到 UserService

    @Test
    void getUserInfo_ShouldReturnUserInfo_WhenUserExists() {
        // 1. 准备测试数据
        Long userId = 1L;
        User mockUser = new User.UserBuilder()
                .name("Alice").email("alice@example.com").build();

        // 2. 定义 Mock 行为：当调用 findById(1L) 时返回 mockUser
        when(userRepository.findById(userId)).thenReturn(mockUser);

        // 3. 执行被测方法
        String result = userService.getUserInfo(userId);

        // 4. 断言结果
        assertEquals("Name: Alice, Email: alice@example.com", result);

        // 5. 验证交互：findById 被调用且仅被调用1次
        verify(userRepository, times(1)).findById(userId);
        // 验证没有其他任何交互发生
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUserInfo_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // 1. 定义 Mock 行为：返回 null
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(null);

        // 2. 执行
        String result = userService.getUserInfo(userId);

        // 3. 断言
        assertEquals("User not found", result);

        // 4. 验证
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createUser_ShouldReturnTrue_WhenUserDoesNotExist() {
        // 1. 准备
        User newUser = new User(3L, "Bob", "bob@example.com");
        when(userRepository.findById(3L)).thenReturn(null);

        // 2. 执行
        boolean created = userService.createUser(newUser);

        // 3. 断言
        assertTrue(created);

        // 4. 验证：先检查是否存在，然后保存
        verify(userRepository).findById(3L);
        verify(userRepository).save(newUser);  // 验证 save 方法被调用且参数正确
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createUser_ShouldReturnFalse_WhenUserAlreadyExists() {
        // 1. 准备
        User existingUser = new User(4L, "Charlie", "charlie@example.com");
        when(userRepository.findById(4L)).thenReturn(existingUser);

        // 2. 执行
        boolean created = userService.createUser(existingUser);

        // 3. 断言
        assertFalse(created);

        // 4. 验证：只调用了 findById，从未调用 save
        verify(userRepository).findById(4L);
        verify(userRepository, never()).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUser_ShouldCallRepositoryDeleteById() {
        // 1. 执行
        userService.deleteUser(5L);

        // 2. 验证：deleteById 被调用一次
        verify(userRepository, times(1)).deleteById(5L);
        verifyNoMoreInteractions(userRepository);
    }
}