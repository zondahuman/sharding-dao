package com.abin.lee.sharding.dbtable.api.aop;

import com.abin.lee.sharding.dbtable.api.datasource.DynamicDataSourceContextHolder;
import com.abin.lee.sharding.dbtable.api.datasource.SelectIdentity;
import com.abin.lee.sharding.dbtable.api.enums.BusinessNameType;
import com.abin.lee.sharding.dbtable.api.enums.LocationSwitchEnum;
import com.abin.lee.sharding.dbtable.api.util.ShardingExchange;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(-1) // 保证该AOP在@Transactional之前执行
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Autowired
    ShardingExchange shardingExchange;

    @Pointcut("execution(* com.abin.lee.sharding.dbtable.api.service.impl..*.*(..))")
    public void pointCut() {
    }

    //    @Before(value = "pointCut()" + " && @annotation(SelectIdentity)" )
    @Before("@annotation(selectIdentity)")
//    @Before("execution(@SelectIdentity public * com.abin.lee.sharding.dbtable.api.service.impl..*.*(..))")
//    @Before("execution(public * com.abin.lee.sharding.dbtable.api.service.impl..*.*(..))")
    public void beforeSwitch(JoinPoint joinPoint, SelectIdentity selectIdentity) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName(); //获取方法名称
        Object[] args = joinPoint.getArgs();//参数
        //获取参数名称和值
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);
        System.out.println(nameAndArgs.toString());

        Boolean flag = LocationSwitchEnum.localtionId(methodName);
        Long functionId = null;
        if (flag) {
            functionId = (long) nameAndArgs.get("id");
        } else {
            functionId = (long) nameAndArgs.get("id");
        }
        Long dbId = this.shardingExchange.selectShardingDatabase(functionId);

        String typeName = typeName(joinPoint);
        String businessName = businessType(joinPoint);
        String dataSource = businessName + "" + dbId + "-" + typeName;
        log.info("DATABASE-------------= " + dataSource);
        //为了省事，其他代码就不写了，
        // 切换数据源
        DynamicDataSourceContextHolder.setDataSourceType(dataSource);

    }

    public static String businessType(JoinPoint joinPoint) throws ClassNotFoundException {
        String result = "";
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String resultName = clazzName.toLowerCase();
        if (resultName.contains(BusinessNameType.order.name())) {
            result = BusinessNameType.order.name();
        } else {
            result = BusinessNameType.business.name();
        }
        return result;
    }

    public static String typeName(JoinPoint joinPoint) throws Throwable {
        String result = "";
        String method = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Class<?> classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method m = classz.getMethod(method, parameterTypes);
        SelectIdentity selectIdentity = m.getAnnotation(SelectIdentity.class);
        if (selectIdentity != null) {
            result = selectIdentity.source().name();
        }
        return result;
    }

    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }

        //Map<>
        return map;
    }


    @After("@annotation(selectIdentity)")
    public void afterSwitch(JoinPoint point, SelectIdentity selectIdentity) {

        DynamicDataSourceContextHolder.clearDataSourceType();

    }

}
