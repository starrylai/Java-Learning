package org.example;

import org.example.StudentCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
@Validated
public class StudentController {

    /**
     * 创建学生（模拟）
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createStudent(@Valid @RequestBody StudentCreateRequest request) {
        // 模拟保存成功，返回学生信息
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "学生创建成功");
        response.put("data", request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
