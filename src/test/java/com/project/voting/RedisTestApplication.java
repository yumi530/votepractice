package com.project.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RedisTestApplication {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisTestApplication(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisTestApplication.class, args);
    }

    public void performRedisTest() {
        // 데이터 저장
        redisTemplate.opsForValue().set("myKey", "Hello, Redis!");

        // 데이터 읽어오기
        String value = redisTemplate.opsForValue().get("myKey");
        System.out.println("Value from Redis: " + value);
    }
}
