package com.project.voting.config;

import java.time.Duration;

public interface RedisService {
    void setValues(String key, String data);

    void setValues(String key, String data, Duration duration);

    String getValue(String key);

    void deleteValues(String key);

    boolean hasKey(String key);
}
