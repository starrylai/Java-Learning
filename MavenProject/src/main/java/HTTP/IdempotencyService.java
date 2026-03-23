package HTTP;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IdempotencyService {
    // 键 -> 响应结果（序列化后的字符串）或状态
    private final ConcurrentHashMap<String, Object> store = new ConcurrentHashMap<>();

    public boolean hasKey(String key) {
        return store.containsKey(key);
    }

    public void saveResult(String key, Object result) {
        store.put(key, result);
    }

    public Object getResult(String key) {
        return store.get(key);
    }
}
