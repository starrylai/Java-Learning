package org.example;

public interface UserRepository {
    User findById(Long id);
    void save(User user);
    void deleteById(Long id);
}