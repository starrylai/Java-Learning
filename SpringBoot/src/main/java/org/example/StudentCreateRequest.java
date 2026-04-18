package org.example;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentCreateRequest {

    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 20, message = "姓名长度必须在2-20之间")
    private String name;

    @NotNull(message = "年龄不能为空")
    @Min(value = 1, message = "年龄必须大于0")
    private Integer age;

    @NotBlank(message = "班级不能为空")
    private String className;

    // 无参构造（必须）
    public StudentCreateRequest() {
    }

    // 全参构造（可选）
    public StudentCreateRequest(String name, Integer age, String className) {
        this.name = name;
        this.age = age;
        this.className = className;
    }

    // Getter / Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}