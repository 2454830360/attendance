package com.system.attendance.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis缓存的配置
 * @author Administrator
 *
 */
@Configuration
public class RedisCacheConfiguration extends CachingConfigurerSupport{
	
	/*@Bean
	public CacheManager getCacheManager(RedisTemplate<?, ?> redisTemplate){
		RedisCacheManager manager = new RedisCacheManager(redisTemplate);
		//配置缓存
		manager.setDefaultExpiration(20);
		Map<String, Long> expires = new HashMap<String, Long>();
		expires.put("zyCache", 200L);
		manager.setExpires(expires);
		return manager;
	}*/
	
}
