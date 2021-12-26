package com.www.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>@Description redis工具类 </p>
 * <p>@Version 1.0 </p>
 * <p>@Author www </p>
 * <p>@Date 2021/8/1 21:07 </p>
 */
@Component
public final class RedisUtils {
    private static RedisTemplate<String,Object> redisTemplate;
    /**
     * <p>@Description 返回redisTemplate实例 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     */
    public static RedisTemplate<String,Object> getRedisTemplate(){
        return redisTemplate;
    }
    /**
     * <p>@Description 判断key值是否存在 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @return boolean true存在，false不存在
     */
    public static boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
    /**
     * <p>@Description 删除key </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/17 20:56 </p>
     * @param key 键
     * @return boolean true删除成功，false失败
     */
    public static boolean deleteKey(String key){
        return redisTemplate.delete(key);
    }
    /**
     * <p>@Description 删除模糊key名 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/11/17 20:56 </p>
     * @param key 模糊键
     * @return boolean true删除成功，false失败
     */
    public static boolean deleteFuzzyKey(String key){
        Set<String> keys = redisTemplate.keys(key);
        if(CollectionUtils.isNotEmpty(keys)){
            redisTemplate.delete(keys);
        }
        return false;
    }
    /**
     * <p>@Description 保存String数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @param value 值
     * @return java.lang.Object
     */
    public static String set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
        return value;
    }
    /**
     * <p>@Description 保存String数据,并设置超时时间 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @param value 值
     * @param seconds 超时时间(秒)
     * @return java.lang.Object
     */
    public static Object set(String key,Object value,long seconds){
        redisTemplate.opsForValue().set(key,value,seconds,TimeUnit.SECONDS);
        return value;
    }
    /**
     * <p>@Description 获取分布式锁 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @param value 值
     * @return boolean true获取锁成功，false获取锁失败
     */
    public static boolean lock(String key,String value){
        return setNX(key,value);
    }
    /**
     * <p>@Description 释放分布式锁 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @param value 值
     * @return boolean true释放锁成功，false释放锁失败
     */
    public static boolean unlock(String key,String value){
        // 判断是否是当前线程获取分布式锁，是则删除key
        if (StringUtils.equals(value,get(key))){
            RedisUtils.deleteKey(key);
            return true;
        }
        return false;
    }

    /**
     * <p>@Description key不存在时保存String数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @param value 值
     * @return boolean true键不存在，保存成功，false键存在，保存失败
     */
    public static boolean setNX(String key,String value){
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }
    /**
     * <p>@Description key不存在时保存String数据，并设置超时时间 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @param value 值
     * @param seconds 超时时间(秒)
     * @return boolean true键不存在，保存成功，false键存在，保存失败
     */
    public static boolean setNX(String key,String value,long seconds){
        return redisTemplate.opsForValue().setIfAbsent(key,value,seconds,TimeUnit.SECONDS);
    }
    /**
     * <p>@Description 获取String数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:07 </p>
     * @param key 键值
     * @return java.lang.Object
     */
    public static String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }
    /**
     * <p>@Description 保存Hash数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:08 </p>
     * @param key 键值
     * @param okey 对象键值
     * @param value 值
     * @return java.lang.Object
     */
    public static Object hashSet(String key, String okey, Object value){
        redisTemplate.opsForHash().put(key,okey,value);
        return value;
    }
    /**
     * <p>@Description 获取存储在哈希表中指定字段的值 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:08 </p>
     * @param key 键值
     * @param okey 对象字段
     * @return java.lang.Object
     */
    public static Object hashGet(String key, String okey){
        return redisTemplate.opsForHash().get(key,okey);
    }
    /**
     * <p>@Description 获取在哈希表中指定 key 的所有字段和值 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:08 </p>
     * @param key 键值
     * @return java.lang.Object
     */
    public static Object hashGet(String key){
        return redisTemplate.opsForHash().values(key);
    }
    /**
     * <p>@Description 从左边保存List数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:08 </p>
     * @param key 键值
     * @param value 值
     * @return java.lang.Object
     */
    public static Object listSet(String key, Object value){
        redisTemplate.opsForList().leftPush(key,value);
        return value;
    }
    /**
     * <p>@Description 获取List集合中所有数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:09 </p>
     * @param key 键值
     * @return java.lang.Object
     */
    public static Object listGet(String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }
    /**
     * <p>@Description 保存Set数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:09 </p>
     * @param key 键值
     * @param value 值
     * @return java.lang.Object
     */
    public static Object setSet(String key, Object value){
       redisTemplate.opsForSet().add(key,value);
        return value;
    }
    /**
     * <p>@Description 返回Set集合中的所有数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:09 </p>
     * @param key 键值
     * @return java.lang.Object
     */
    public static Object setGet(String key){
        return redisTemplate.opsForSet().members(key);
    }
    /**
     * <p>@Description 保存ZSet数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:09 </p>
     * @param key 键值
     * @param value 值
     * @param score 分值
     * @return java.lang.Object
     */
    public static Object zsetSet(String key, Object value, double score){
        redisTemplate.opsForZSet().add(key,value,score);
        return value;
    }
    /**
     * <p>@Description 获取ZSet数据 </p>
     * <p>@Author www </p>
     * <p>@Date 2021/8/1 21:09 </p>
     * @param key 键值
     * @return java.lang.Object
     */
    public static Object zsetGet(String key){
        return redisTemplate.opsForZSet().range(key,0,-1);
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }
}
