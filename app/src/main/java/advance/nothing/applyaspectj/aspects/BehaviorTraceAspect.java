package advance.nothing.applyaspectj.aspects;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import advance.nothing.applyaspectj.annotations.BehaviorTrace;

@Aspect
public class BehaviorTraceAspect {

    private final String tag = this.getClass().getSimpleName();

    @Pointcut("execution(@advance.nothing.applyaspectj.annotations.BehaviorTrace * *(..))")
    public void methodAnnotated(){
    }

    // 和methodAnnotated()方法关联
    @Around("methodAnnotated()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result=null;
        if(null!=joinPoint){
            MethodSignature methodSignature=(MethodSignature)joinPoint.getSignature();
            String className=methodSignature.getDeclaringType().getSimpleName();
            String methodName=methodSignature.getName();
            String funName=methodSignature.getMethod().getAnnotation(BehaviorTrace.class).value();
            Log.e(tag,"before method invoke");

            // beginTime
            long begin=System.currentTimeMillis();
            // 调用proceed才会去触发方法调用
            result=joinPoint.proceed();

            long duration=System.currentTimeMillis()-begin;
            Log.e(tag,String.format("after method invoke,关注的功能标签：%s，%s类的%s方法被调用，耗时%d ms",funName,className,methodName,duration));
            return result;
        }
        return result;
    }


}
