package com.enreach.ssm.infrastructure.cache;

import com.enreach.ssm.utils.SerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class RedisCache {

    /**
     * 缓存名前缀
     */
    public final static String CACHE_PREFIX = "cache|";

    /**
     * 默认缓存时间
     */
    public final static int CACHE_TIME = 60;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> boolean putCache(String key, T obj) {

        if (!key.startsWith(CACHE_PREFIX)) {
            key = CACHE_PREFIX + key;
        }

        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serialize(obj);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(bkey, bvalue);
            }
        });

        return result;
    }

    public <T> void putCacheWithExpireTime(String key, T obj, final long expireTime) {

        if (!key.startsWith(CACHE_PREFIX)) {
            key = CACHE_PREFIX + key;
        }

        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serialize(obj);
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireTime, bvalue);
                return true;
            }
        });
    }

    public <T> boolean putListCache(String key, List<T> objList) {

        if (!key.startsWith(CACHE_PREFIX)) {
            key = CACHE_PREFIX + key;
        }

        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serializeList(objList);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(bkey, bvalue);
            }
        });
        return result;
    }

    public <T> boolean putListCacheWithExpireTime(String key, List<T> objList, final long expireTime) {

        if (!key.startsWith(CACHE_PREFIX)) {
            key = CACHE_PREFIX + key;
        }

        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializerUtil.serializeList(objList);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireTime, bvalue);
                return true;
            }
        });
        return result;
    }

    public <T> T getCache(String key, Class<T> targetClass) {

        if (!key.startsWith(CACHE_PREFIX)) {
            key = CACHE_PREFIX + key;
        }

        final byte[] bkey = key.getBytes();

        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(bkey);
            }
        });
        if (result == null) {
            return null;
        }
        return SerializerUtil.deserialize(result, targetClass);
    }

    public <T> List<T> getListCache(String key, Class<T> targetClass) {
        if (!key.startsWith(CACHE_PREFIX)) {
            key = CACHE_PREFIX + key;
        }

        final byte[] bkey = key.getBytes();

        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(bkey);
            }
        });
        if (result == null) {
            return null;
        }
        return SerializerUtil.deserializeList(result, targetClass);
    }

    /**
     * 精确删除key
     *
     * @param key
     */
    public void deleteCache(String key) {

        if (!key.startsWith(CACHE_PREFIX)) {
            key = CACHE_PREFIX + key;
        }

        redisTemplate.delete(key);
    }

    /**
     * 模糊删除key
     *
     * @param pattern
     */
    public void deleteCacheWithPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 清空所有缓存
     */
    public void clearAllCache() {
        deleteCacheWithPattern(RedisCache.CACHE_PREFIX + "*");
    }
}
