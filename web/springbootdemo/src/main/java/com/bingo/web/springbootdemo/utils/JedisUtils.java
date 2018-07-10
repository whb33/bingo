/*
package com.bingo.web.springbootdemo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

*/
/**
 * Jedis工具类（单机）
 *//*

@Component
public class JedisUtils {
	
	@Resource
    private JedisPool jedisPool;
	
    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
    private static RedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
    private static RedisSerializer<String> keySerializer = new StringRedisSerializer();
    public final static int EXPIRE_ONE_DAY = 60 * 60 * 24;
    public final static int EXPIRE_ONE_HOUR = 60 * 60;
    public final static int EXPIRE_ONE_MIN = 60;

    private JedisUtils() {
    }

    private void returnResource(Jedis jedis) {
        if (null != jedis) {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                logger.error("could not return resource:{}", e.getMessage(), e);
            }
        }
    }

    private JedisPool getPool() {
        return jedisPool;
    }

    private Jedis getResource() {
        return jedisPool.getResource();
    }
    
    public Object get(String key) {
        if (logger.isDebugEnabled()) {
            logger.debug("get(key-->{})", key);
        }
        Jedis jedis = null;
        byte[] bytes = null;
        try {
            jedis = getResource();
            bytes = jedis.get(keySerializer.serialize(key));
        } catch (Exception e) {
            logger.error("get(key-->{}) error:{}", key, e.getMessage());
        } finally {
            returnResource(jedis);
        }
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes);
    }
    
    public Object getSerializer(String key) {
    	if (logger.isDebugEnabled()) {
            logger.debug("get(key-->{})", key);
        }
        Jedis jedis = null;
        byte[] bytes = null;
        try {
            jedis = getResource();
            bytes = jedis.get(keySerializer.serialize(key));
        } catch (Exception e) {
            logger.error("get(key-->{}) error:{}", key, e.getMessage());
        } finally {
            returnResource(jedis);
        }
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return valueSerializer.deserialize(bytes);
    }

    */
/**
     * redis存储数据,默认一小时
     *
     * @param key
     * @param value
     *//*

    public void set(String key, Object value) {
        set(key, value, EXPIRE_ONE_HOUR);
    }

    */
/**
     * redis存储数据，设置超时时间，小于0则永不过期
     *
     * @param key
     * @param value
     * @param expire 超时时间
     *//*

    public void set(String key, Object value, int expire) {
        if (logger.isDebugEnabled()) {
            logger.debug("set(key-->{},value-->{},expire-->{})", key, value, expire);
        }
        Jedis jedis = null;
        byte[] k;
        byte[] v;
        try {
            jedis = getResource();
            k = keySerializer.serialize(key);
            v = valueSerializer.serialize(value);
            if (expire > 0) {
                jedis.setex(k, expire, v);
            } else {
                jedis.set(k, v);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("set(key-->{}) error:{}", key, e.getMessage());
        } finally {
            returnResource(jedis);
        }
    }
    
    public void setSerialize(String key, Object value, int expire) {
        if (logger.isDebugEnabled()) {
            logger.debug("set(key-->{},value-->{},expire-->{})", key, value, expire);
        }
        Jedis jedis = null;
        byte[] k;
        byte[] v;
        try {
            jedis = getResource();
            v = valueSerializer.serialize(value);
            if (expire > 0) {
                jedis.setex(new String(key), expire,new String(v));
            } else {
                jedis.set(key,new String(v));
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("set(key-->{}) error:{}", key, e.getMessage());
        } finally {
            returnResource(jedis);
        }
    }

    public void del(String key) {
        if (logger.isDebugEnabled()) {
            logger.debug("del(key-->{})", key);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.del(keySerializer.serialize(key));
        } catch (Exception e) {
            logger.error("del(key-->{}) error:{}", key, e.getMessage());
        } finally {
            returnResource(jedis);
        }
    }

    public Boolean exists(String key) {
        if (logger.isDebugEnabled()) {
            logger.debug("exists(key-->{})", key);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error("exists(key-->{}) error:{}", key, e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    */
/**
     * 获取包含该前缀的所有key集合
     *
     * @param prefix key前缀
     * @return Set<String>
     *//*

    public Set<String> keys(String prefix) {
        if (logger.isDebugEnabled()) {
            logger.debug("keys(prefix-->{})", prefix);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.keys(prefix + "*");
        } catch (Exception e) {
            logger.error("keys(prefix-->{}) error:{}", prefix, e.getMessage());
        } finally {
            returnResource(jedis);
        }
		return null;
    }

    public Long hincrBy(String key, String field, long value) {
        if (logger.isDebugEnabled()) {
            logger.debug("hincrBy(key-->{},field-->{},value-->{})", key, field, value);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            logger.error("hincrBy(key-->{},field-->{},value-->{}) error:{}", key, field, value, e.getMessage());
        } finally {
            returnResource(jedis);
        }
		return value;
    }

    public Boolean hset(String key, String field, String value) {
        if (logger.isDebugEnabled()) {
            logger.debug("hset(key-->{},field-->{},value-->{})", key, field, value);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hset(key, field, value) > 0;
        } catch (Exception e) {
            logger.error("hset(key-->{},field-->{},value-->{}) error:{}", key, field, value, e.getMessage());
        } finally {
            returnResource(jedis);
        }
		return null;
    }

    public String hget(String key, String field) {
        if (logger.isDebugEnabled()) {
            logger.debug("hget(key-->{},field-->{}) error:{}", key, field);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return exists(key) ? jedis.hget(key, field) : null;
        } catch (Exception e) {
            logger.error("hget(key-->{},field-->{}) error:{}", key, field, e.getMessage());
        } finally {
            returnResource(jedis);
        }
		return null;
    }

    public boolean hexists(String key, String field) {
        if (logger.isDebugEnabled()) {
            logger.debug("hexists(key-->{},field-->{}) error:{}", key, field);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hexists(key, field);
        } catch (Exception e) {
            logger.error("hexists(key-->{},field-->{}) error:{}", key, field, e.getMessage());
        } finally {
            returnResource(jedis);
        }
		return false;
    }

    public boolean setnx(String key, String value) {
        if (logger.isDebugEnabled()) {
            logger.debug("setnx(key-->{},value-->{}) error:{}", key, value);
        }
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.setnx(key, value) == 1L;
        } catch (Exception e) {
            logger.error("setnx(key-->{},value-->{}) error:{}", key, value, e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    public List<String> hkeys(String key){
    	Jedis jedis = null;
        try {
            jedis = getResource();
            Set<String> iter = jedis.hkeys(key);
            return new ArrayList<String>(iter);
        } catch (Exception e) {
            logger.error("hkeys(key-->{}) error:{}",key,e.getMessage());
        } finally {
            returnResource(jedis);
        }
        return null;
    }
}*/
