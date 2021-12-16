package org.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public void set(String key, Object value) {
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
        } catch (Exception e) {
            log.error("redis service set object error,key is:{}", key);
        }
    }

    public void set(String key, Object value, long expireTime, TimeUnit timeUnit) {
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
        } catch (Exception e) {
            log.error("redis service set object error,key:{},expire time:{}", key, expireTime);
        }
    }

    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public void removePattern(String pattern) {
        Set<Object> keys = redisTemplate.keys(pattern);
        if (keys != null && keys.size() > 0) {
            try {
                redisTemplate.delete(keys);
            } catch (Exception e) {
                log.error("redis service remove pattern error,pattern:{}", pattern);
            }
        }
    }

    public void remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("redis service delete object error,key:{}", key);
        }
    }

    public boolean exists(String key) {
        if (key == null) {
            return false;
        }
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("redis service check exists object error,key:{}", key);
        }
        return false;
    }

    public Object get(String key) {
        try {
            ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        } catch (Exception e) {
            log.error("redis service get object error,key:{}", key);
        }
        return null;
    }

    public void hashSet(String key, Object hashKey, Object value) {
        try {
            HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
            hash.put(key, hashKey, value);
        } catch (Exception e) {
            log.error("redis service hash set object error,key:{}", key);
        }
    }

    public Object hashGet(String key, Object hashKey) {
        try {
            HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
            return hash.get(key, hashKey);
        } catch (Exception e) {
            log.error("redis service hash get object error,key:{}", key);
        }
        return null;
    }

    public void listPush(String key, Object value) {
        try {
            ListOperations<Object, Object> list = redisTemplate.opsForList();
            list.rightPush(key, value);
        } catch (Exception e) {
            log.error("redis service list push object error,key:{}", key);
        }
    }

    public List<Object> listRange(String key, long start, long end) {
        try {
            ListOperations<Object, Object> list = redisTemplate.opsForList();
            return list.range(key, start, end);
        } catch (Exception e) {
            log.error("redis service list range object error,key:{}", key);
        }
        return null;
    }

    public void setAdd(String key, Object value) {
        try {
            SetOperations<Object, Object> set = redisTemplate.opsForSet();
            set.add(key, value);
        } catch (Exception e) {
            log.error("redis service set add object error,key:{}", key);
        }
    }

    public Set<Object> setMembers(String key) {
        try {
            SetOperations<Object, Object> set = redisTemplate.opsForSet();
            return set.members(key);
        } catch (Exception e) {
            log.error("redis service set members object error,key:{}", key);
        }
        return null;
    }

    public void sortAdd(String key, Object value, double score) {
        try {
            ZSetOperations<Object, Object> sortSet = redisTemplate.opsForZSet();
            sortSet.add(key, value, score);
        } catch (Exception e) {
            log.error("redis service sort add object error,key:{}", key);
        }
    }

    public Set<Object> rangeScore(String key, double minScore, double maxScore) {
        try {
            ZSetOperations<Object, Object> sortSet = redisTemplate.opsForZSet();
            return sortSet.rangeByScore(key, minScore, maxScore);
        } catch (Exception e) {
            log.error("redis service range score object error,key:{}", key);
        }
        return null;
    }
}