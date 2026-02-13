package org.example;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.*;

@Slf4j
public class App {
//    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        log.trace("TRACE 级别消息：这是最细粒度的调试信息");
        log.debug("DEBUG 级别消息：当前方法入参 userId={}, orderId={}", "alice", "10086");
        log.info("INFO 级别消息：用户登录成功，userId={}", "alice");
        log.warn("WARN 级别消息：磁盘空间剩余不足，当前剩余 {} GB", 1.2);
        log.error("ERROR 级别消息：数据库连接超时，重试次数={}", 3);

        // 模拟大量日志，触发滚动分片
        for (int i = 0; i < 100000; i++) {
            log.info("模拟业务日志，序号：{}，内容：{}", i, "这是一条测试日志，用于触发日志文件滚动。");
        }

        log.info("日志滚动测试结束。");
    }
}