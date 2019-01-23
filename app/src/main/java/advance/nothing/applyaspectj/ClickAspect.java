package advance.nothing.applyaspectj;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ClickAspect {

    private final String tag = this.getClass().getSimpleName();

    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickMethod(){}

    @Around("clickMethod()")
    public void onClickMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        View view = null;
        for (Object arg : args) {
            if (arg instanceof View){
                view = (View) arg;
            }
        }
        // 分别对应字段变量名和资源id
        String varName = null;
        String resName = null;
        if (view != null){
            varName = view.getContext().getResources().getResourceEntryName(view.getId());
            resName = view.getContext().getResources().getResourceName(view.getId());
        }
        joinPoint.proceed();
        Log.e(tag,"click ... " + varName + " " +  resName);
    }

}
