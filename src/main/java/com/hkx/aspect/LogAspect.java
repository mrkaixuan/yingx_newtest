package com.hkx.aspect;

import com.hkx.annotation.AddLog;
import com.hkx.entity.Admin;
import com.hkx.entity.Log;
import com.hkx.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect //切面类
@Configuration //表示一个配置类
public class LogAspect {

    @Resource
    HttpServletRequest request;
    @Resource
    LogService logService;

    //环绕通知/后置通知   :这里牵扯到了操作是否成功,基于注解的切面
    @Around("@annotation(com.hkx.annotation.AddLog)") //配置切入点, 注解的全限定名
    public Object addLogs(ProceedingJoinPoint proceedingJoinPoint){

        //获得切到的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法信息
        Method method = signature.getMethod();
        //获取方法上的注解   哪个注解 反射
        AddLog addLog = method.getAnnotation(AddLog.class);
        //获取注解中的值    value
        String value = addLog.value();

        //谁   时间   操作   是否成功
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //时间
        Date date = new Date();
        //操作
        String methodName = proceedingJoinPoint.getSignature().getName();
        try {
            //放行方法
            Object proceed = proceedingJoinPoint.proceed();
            String message="success";
            //日志信息入库
            System.out.println("管理员："+admin.getUsername()+"--时间："+date+"--方法："+value+"("+methodName+")"+"--是否成功“"+message);
            Log log = new Log(UUID.randomUUID().toString(),admin.getUsername(),date,value+"("+methodName+")",message);

            logService.addLog(log);//日志信息入库
            System.out.println(log);

            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return null;
        }
    }

    //环绕通知 根据切入点表达式
    //@Around("execution(* com.baizhi.serviceImpl.*.*(..)) && !execution(* com.baizhi.serviceImpl.*.query*(..)) ")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){

        //谁   时间   操作   是否成功
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        //时间: 就是切入的时间
        Date date = new Date();
        //操作
        String methodName = proceedingJoinPoint.getSignature().getName();//获得方法名
        try {
            //放行方法
            Object proceed = proceedingJoinPoint.proceed(); //执行方法
            String message="success";
            System.out.println(admin.getUsername()+"--"+date+"--"+methodName+"--"+message);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message="error";
            return null;
        }
    }
}
