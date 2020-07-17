package com.niewj.aop;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class BizRequestLogAspects {

    /**
     * 下面的各个方法切入点医院, 可以抽取一个方法
     * 1. 方法名随意, 比如: myPointCut;
     * 2. 空实现即可;
     */
    @Pointcut("execution(public String com.niewj.aop.BizRequest.*(..))")
    public void myPointCut() {
    }

    @Before("myPointCut()") // 或者 外部用可以写全名: com.niewj.aop.BizRequestLogAspects.myPointCut()
    public void logStart(JoinPoint joinPoint) {
        long time = System.currentTimeMillis();
        String bizMethodName = joinPoint.getSignature().getName();
        String bizMethodParamJson = new Gson().toJson(joinPoint.getArgs());

        // 现实生产中, 可能会把日志信息封装成一个对象, 可以持久化记录到 mongo 或者 hbase中,
        // 以便将来提供消费查询服务
        LogInfo logInfo = new LogInfo();
        logInfo.setBizMethodName(bizMethodName);
        logInfo.setBizMethodParamsJson(bizMethodParamJson);
        logInfo.setLogTime(time);
        logInfo.setLogType(LogTypeEnum.BEFORE);
        System.out.println(logInfo);
    }

    @After(("myPointCut()"))
    public void logStop(JoinPoint joinPoint) {
        long time = System.currentTimeMillis();
        String bizMethodName = joinPoint.getSignature().getName();

        // 现实生产中, 可能会把日志信息封装成一个对象, 可以持久化记录到 mongo 或者 hbase中,
        // 以便将来提供消费查询服务
        LogInfo logInfo = new LogInfo();
        logInfo.setBizMethodName(bizMethodName);
        logInfo.setLogTime(time);
        logInfo.setLogType(LogTypeEnum.AFTER);
        System.out.println(logInfo);
    }

    @AfterReturning(value = "myPointCut()", returning = "rst")
    public void logReturn(JoinPoint joinPoint, Object rst) {
        long time = System.currentTimeMillis();
        String bizMethodName = joinPoint.getSignature().getName();
        String bizMethodParamJson = new Gson().toJson(joinPoint.getArgs());

        // 现实生产中, 可能会把日志信息封装成一个对象, 可以持久化记录到 mongo 或者 hbase中,
        // 以便将来提供消费查询服务
        LogInfo logInfo = new LogInfo();
        logInfo.setBizMethodName(bizMethodName);
        logInfo.setBizMethodParamsJson(bizMethodParamJson);
        logInfo.setLogTime(time);
        logInfo.setLogType(LogTypeEnum.AFTER_RETURNING);
        logInfo.setReturnJson(String.valueOf(rst));
        System.out.println(logInfo);
    }

    @AfterThrowing(value = "myPointCut()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        long time = System.currentTimeMillis();
        String bizMethodName = joinPoint.getSignature().getName();
        String bizMethodParamJson = new Gson().toJson(joinPoint.getArgs());

        // 现实生产中, 可能会把日志信息封装成一个对象, 可以持久化记录到 mongo 或者 hbase中,
        // 以便将来提供消费查询服务
        LogInfo logInfo = new LogInfo();
        logInfo.setBizMethodName(bizMethodName);
        logInfo.setBizMethodParamsJson(bizMethodParamJson);
        logInfo.setLogTime(time);
        logInfo.setLogType(LogTypeEnum.AFTER_THROWING);
        logInfo.setException(ex);
        System.out.println(logInfo);
    }
}
