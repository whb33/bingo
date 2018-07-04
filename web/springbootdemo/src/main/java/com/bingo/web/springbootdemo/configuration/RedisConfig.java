package com.bingo.web.springbootdemo.configuration;

import com.bingo.web.springbootdemo.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisConfig {
	
	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
	
	@Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        rcm.setDefaultExpiration(60);//秒
        return rcm;
    }
    
    @Bean
	public RedisConnectionFactory redisConnectionFactory() {
    	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
		jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
		jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
		jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
		jedisPoolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
		jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(redisProperties.getSoftMinEvictableIdleTimeMillis());
		JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig);
		factory.setHostName(redisProperties.getIp());
		factory.setPort(redisProperties.getPort());
	    return factory;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		RedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
	    RedisSerializer<String> keySerializer = new StringRedisSerializer();
	    template.setValueSerializer(valueSerializer);
	    template.setKeySerializer(keySerializer);
	    template.afterPropertiesSet();
        return template;
    }

}
