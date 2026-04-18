package org.example;  // 根据你的实际包名修改

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public Map<String, String> sayHello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, Spring Boot!");
        // 也可以返回其他字段，如时间戳等
        return response;
    }
}