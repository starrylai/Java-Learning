package org.example;

import org.example.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {

    @Autowired
    private AppProperties appProperties;

    @GetMapping("/info")
    public Map<String, String> getAppInfo() {
        Map<String, String> response = new HashMap<>();
        response.put("title", appProperties.getTitle());
        response.put("owner", appProperties.getOwner());
        return response;
    }
}
