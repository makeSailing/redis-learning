package com.makesailing.neo.redis;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * #
 *
 * @author jamie
 * @date 2018/11/19 17:27
 */
public class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// *************************  common *******************************

	/**
	 * 指定缓存的失效时间
	 *
	 * @param time 单位 秒
	 */
	public boolean expire(String key, Long time) {
		return time > 0 ? redisTemplate.expire(key, time, TimeUnit.SECONDS) : false;
	}

	/**
	 * 获取key的过期时间
	 */
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 删除缓存
	 *
	 * @param keys 可以传一个或者多个
	 */
	public void del(String... keys) {
		if (Objects.nonNull(keys) && keys.length > 0) {
			if (keys.length == 1) {
				redisTemplate.delete(keys[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(keys));
			}
		}
	}

	// *************************  string *******************************

	/**
	 * 普通缓存获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
		return Objects.isNull(key) ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入
	 *
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 普通缓存放入并设置时间
	 *
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 递增
	 *
	 * @param key 键
	 * @param by 要增加几(大于0)
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 *
	 * @param key 键
	 * @param by 要减少几(小于0)
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// *************************  hash *******************************
	/**
	 * HashGet
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	public Object hget(String key,String item){
		return redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<Object,Object> hmget(String key){
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * HashSet
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean hmset(String key, Map<String,Object> map){
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public boolean hmset(String key, Map<String,Object> map, long time){
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if(time>0){
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public boolean hset(String key,String item,Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean hset(String key,String item,Object value,long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if(time>0){
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除hash表中的值
	 * @param key 键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public void hdel(String key, Object... item){
		redisTemplate.opsForHash().delete(key,item);
	}

	/**
	 * 判断hash表中是否有该项的值
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public boolean hHasKey(String key, String item){
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * @param key 键
	 * @param item 项
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public double hincr(String key, String item,double by){
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * hash递减
	 * @param key 键
	 * @param item 项
	 * @param by 要减少记(小于0)
	 * @return
	 */
	public double hdecr(String key, String item,double by){
		return redisTemplate.opsForHash().increment(key, item,-by);
	}
	// *************************  list *******************************
	/**
	 * 获取list缓存的内容
	 * @param key 键
	 * @param start 开始
	 * @param end 结束  0 到 -1代表所有值
	 * @return
	 */
	public List<Object> lGet(String key,long start, long end){
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 * @param key 键
	 * @return
	 */
	public long lGetListSize(String key){
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * @param key 键
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object lGetIndex(String key,long index){
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) expire(key, time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) expire(key, time);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 * @param key 键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index,Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 移除N个值为value 
	 * @param key 键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public long lRemove(String key,long count,Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			return 0;
		}
	}
	// *************************  zset *******************************

}


