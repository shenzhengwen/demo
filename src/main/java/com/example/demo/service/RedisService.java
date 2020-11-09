package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
//@SuppressWarnings("unchecked")
@Slf4j
public class RedisService {

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("set key:{},e:{}",key,e);
        }
        return result;
    }


    /**
     * 写入分布式锁key，带有效时间分钟
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setNX(final String key, Object value, Long expireTime) {
        boolean setResult = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            setResult = operations.setIfAbsent(key, value);
            if (setResult) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("setNX key:{},e:{}",key,e);
        }
        return setResult;
    }


    /**
     * 写入分布式锁key，带有效时间小时
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setNXHour(final String key, Object value, Long expireTime) {
        boolean setResult = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            setResult = operations.setIfAbsent(key, value);
            if (setResult) {
                redisTemplate.expire(key, expireTime, TimeUnit.HOURS);
            }
        } catch (Exception e) {
            log.error("setNXHour key:{},e:{}",key,e);
        }
        return setResult;
    }

    /**
     * 写入缓存设置时效时间(小时)
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = redisTemplate.expire(key, expireTime, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("set key:{},e:{}",key,e);
        }
        return result;
    }


    /**
     * 写入缓存设置时效时间,带事务写入
     * 未考虑多并发
     *
     * @param key
     * @param value
     * @return
     */
    public List<Object> setMulti(final String key, Object value, String key2, Object value2, Long expireTime) {
        List<Object> exec = null;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            operations.set(key2, value2);
            redisTemplate.expire(key2, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("setMulti key:{},e:{}",key,e);
        }
        return exec;
    }



    /**
     * 延长token生命周期
     */
    public boolean setExpire(final String key, Long expireTime) {
        boolean result = false;
        try {
            result = redisTemplate.expire(key, expireTime, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("setExpire key:{},e:{}",key,e);
        }
        return result;
    }


    /**
     * 批量获取所有的key
     *
     * @param pattern
     */
    public Set<String> getAllKeyPattern(final String pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }


    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希获取所有数据
     *
     * @param key
     * @param key
     * @return
     */
    public Object hmGetAll(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.keys(key);
    }

    public Object hmDelete(String key, String hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.delete(key, hashKey);


    }


    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPush(k, v);
    }

    /**
     * 列表添加,设置有效时间
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v,Long expireTime) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        redisTemplate.expire(k,expireTime,TimeUnit.MINUTES);
        list.leftPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }


    public Object popAll(String k) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.leftPop(k);
    }

    /**
     * 批量把一个数组插入到列表中
     * @param  @param list
     */
    public Long leftPushAll(String k, List list){
        Long aLong = redisTemplate.opsForList().leftPushAll(k, list);

        return aLong;
    }
    /**
     * 批量把一个数组插入到列表中
     * @param k
     * @param list
     */
    public Long leftPushAllExpireTime(String k, List list,Long expireTime){
        Long aLong = redisTemplate.opsForList().leftPushAll(k, list);
        redisTemplate.expire(k,expireTime,TimeUnit.MINUTES);
        return aLong;
    }

    /**
     * 弹出最右边的元素，弹出之后该值在列表中将不复存在
     * @param k
     * @return
     */
    public String rightTop(String k){
        String o=null;

        List<String> ol = (List)lRange(k,-1,-1);
        if(ol!=null && ol.size()>0) {
            o = ol.get(0);
        }
        return o;
    }

    /**
     * 获取最右边的元素，弹出之后该值在列表中将不复存在
     * @param k
     * @return
     */
    public Object rightPop(String k){
        Object o = redisTemplate.opsForList().rightPop(k);
        return o;
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

}