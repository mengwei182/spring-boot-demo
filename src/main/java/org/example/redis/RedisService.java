package org.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    public ValueOperations<Object, Object> getValueOperations() {
        return redisTemplate.opsForValue();
    }

    public HashOperations<Object, Object, Object> getHashOperations() {
        return redisTemplate.opsForHash();
    }

    public ListOperations<Object, Object> getListOperations() {
        return redisTemplate.opsForList();
    }

    public ZSetOperations<Object, Object> getZSetOperations() {
        return redisTemplate.opsForZSet();
    }

    public SetOperations<Object, Object> getSetOperations() {
        return redisTemplate.opsForSet();
    }

    public ClusterOperations<Object, Object> getClusterOperations() {
        return redisTemplate.opsForCluster();
    }

    public GeoOperations<Object, Object> getGeoOperations() {
        return redisTemplate.opsForGeo();
    }

    public HyperLogLogOperations<Object, Object> getHyperLogLogOperations() {
        return redisTemplate.opsForHyperLogLog();
    }

    public StreamOperations<Object, Object, Object> getStreamOperations() {
        return redisTemplate.opsForStream();
    }

    public void expire(String key, long expireTime, TimeUnit timeUnit) {
        try {
            redisTemplate.expire(key, expireTime, timeUnit);
        } catch (Exception e) {
            log.error("redis service expire object error,key:{},expire time:{},timeUnit:{}", key, expireTime, timeUnit.toString());
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
}