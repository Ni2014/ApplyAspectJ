package advance.nothing.applyaspectj.aspects;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import advance.nothing.applyaspectj.Utils;
import advance.nothing.applyaspectj.annotations.SingleClick;

@Aspect
public class SingleClickAspect {

    private final String tag = this.getClass().getSimpleName();
    @Pointcut("execution(@advance.nothing.applyaspectj.annotations.SingleClick * *(..))")
    public void methodAnnotated(){
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        SingleClick singleClick = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(SingleClick.class);
        if (view != null) {
            if (!Utils.isFastDoubleClick(view, singleClick.value())) {
                //不是快速点击，就直接往下执行方法
                joinPoint.proceed();
            } else {
                Log.e(tag,Utils.getMethodHelperInfo(joinPoint) + "：出现了快速点击，View id为：" + view.getId());
            }
        }
    }
}
