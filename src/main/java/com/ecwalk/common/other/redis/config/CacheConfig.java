package com.ecwalk.common.other.redis.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.Charsets;
import org.apache.curator.shaded.com.google.common.hash.BloomFilter;
import org.apache.curator.shaded.com.google.common.hash.Funnel;
import org.apache.curator.shaded.com.google.common.hash.Funnels;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.ecwalk.common.other.proxy.User;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport{
	
	private BloomFilter<String> bf=null;
	
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory){
		return null;
	}

	/**
	 * 缓存穿透 布隆过滤器
	 */
	@PostConstruct
	public void init(){
		List<User> users=new ArrayList<>();
		User user=new User();
		user.setAddress("1");
		users.add(user);
		
		bf=BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), users.size());
		for(User user1:users){
			bf.put(user.getAddress());
		}
	}
}
