package com.zonekey.study.common;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class BaseRedis {
	private long time =3;
	private TimeUnit timeUnit = TimeUnit.MINUTES;

	@Resource
	protected RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 获取valueOperation
	 * @param <T>
	 * @param <V>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <K, V> ValueOperations<K, V> getValOp(){
		return (ValueOperations<K, V>) redisTemplate.opsForValue();
	}
	
	/**
	 * 获取缓存
	 * @param <V>
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <V> V getCache(String key){
		return  (V) getValOp().get(key);
	}
	
	/**
	 * 设置缓存
	 * @param <V>
	 * @param key
	 * @param time
	 * @param timeUnit
	 */
	public <V> void setCache(String key,V v){
		setCache(key, v, time, timeUnit);
	}
	/**
	 * 设置缓存
	 * @param <V>
	 * @param key
	 * @param time
	 * @param timeUnit
	 */
	public <V> void setCache(String key,V v,long time,TimeUnit timeUnit){
		getValOp().set(key, v, time, timeUnit);
	}
	/**
	 * 删除单个缓存
	 * @param key
	 */
	public void removeSingleCache(String key){
		redisTemplate.delete(key);
	}
	
	/**
	 * 删除系列缓存
	 * @param keys
	 */
	public void removeCaches(Collection<String> keys){
		redisTemplate.delete(keys);
	}
}
