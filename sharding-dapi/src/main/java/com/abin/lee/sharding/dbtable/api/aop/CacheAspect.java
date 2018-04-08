package com.abin.lee.sharding.dbtable.api.aop;

import com.abin.lee.sharding.dbtable.common.util.DateUtil;
import com.abin.lee.sharding.dbtable.common.util.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Date;

//@Aspect
//@Order(-1) // 保证该AOP在@Transactional之前执行
//@Component
@Slf4j
public class CacheAspect {

    private static final String EXECUTION = "@annotation(com.abin.lee.sharding.dbtable.api.aop.Cache)";

    @Pointcut("execution(* com.abin.lee.sharding.dbtable.api.service.impl..*.*(..))")
    public void pointCut() {
    }

    @Around(EXECUTION)
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        ShardingCache cache = (ShardingCache) method.getAnnotation(ShardingCache.class);
        Object result = null;
        String cacheKey = null;
        if (null != cache) {
            try {
                cacheKey = cache.cacheKey();
                int expiry = cache.expiry();
                if (cacheKey == null || cacheKey.equals("")) {
                    String className = pjp.getTarget().getClass().getSimpleName();
                    String methodName = method.getName();
                    cacheKey = StringUtils.join(className, "." + methodName);
                }
                Object[] arguments = pjp.getArgs();
                cacheKey = getCacheKey(cacheKey, arguments);
                byte[] value = null;
//                byte[] value = RedisUtil.getInstance().get(cacheKey.getBytes());
                if (value == null) {
                    result = pjp.proceed();
                    if (cache.cacheNull()) {
                        log.info("nocached,cache all,key:" + cacheKey);
//                        RedisUtil.getInstance().set(cacheKey.getBytes(), SerializeUtil.serializeTransNull(result), expiry);
                    } else if (result != null) {
                        log.info("nocached,cache not null,key:" + cacheKey);
//                        RedisUtil.getInstance().set(cacheKey.getBytes(), SerializeUtil.serializeTransNull(result), expiry);
                    } else {
                        log.info("nocached,no cache null,key:" + cacheKey);
                    }
                } else {
                    result = SerializeUtil.unserializeTransNull(value);
                    log.info("cached,key:" + cacheKey);
                }
            } catch (Exception e) {
                log.error("$$key:" + cacheKey + ":$$" + e.getMessage(), e);
                result = pjp.proceed();
            }

        } else {
            result = pjp.proceed();
        }

        return result;
    }

    private String getCacheKey(String cacheKey, Object[] arguments) {
        StringBuffer sb = new StringBuffer(cacheKey);
        if (arguments != null && arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i] == null) {
                    sb.append("/");
                } else if (arguments[i] instanceof Date) {
                    sb.append("/").append(DateUtil.getYMDHMSTime((Date) arguments[i]));
                } else {
                    sb.append("/").append(arguments[i]);
                }
            }
        }
        return sb.toString();
    }

//    @Override
    public int getOrder() {
        return 1;
    }





}
