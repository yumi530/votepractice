package com.project.voting.service.cache;

import java.time.Duration;
import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

  HashMap<String, String> cache = new HashMap<>();

  public void setValues(String s, String smsConfirmNum, Duration verificationTimeLimit) {
    cache.put(s , smsConfirmNum);
    if (verificationTimeLimit.isZero()){
      cache.remove(s);
    }
  }

  public boolean hasKey(String key) {
    if (cache.containsKey(key)) {
      return true;
    } return false;
  }

  public String getValue(String key) {
    return cache.get(key);
  }

  public void deleteValues(String key) {
    cache.remove(key);
  }
}
