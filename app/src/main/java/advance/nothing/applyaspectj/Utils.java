package advance.nothing.applyaspectj;

import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import advance.nothing.applyaspectj.annotations.SingleClick;

public final class Utils {
    /**
     * 最近一次点击的时间
     */
    private static long sLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int sLastClickViewId;


    private Utils(){}

    public static boolean isFastDoubleClick(View v) {
        return isFastDoubleClick(v, SingleClick.DEFAULT_INTERVAL_MILLIS);
    }

    public static boolean isFastDoubleClick(View view, long intervalMillis) {

        long time = System.currentTimeMillis();
        int viewId = view.getId();
        long duration = time - sLastClickTime;
        // 两次点击的时间间隔没超过阀值且前后两次是同一个View
        if (duration < intervalMillis && viewId == sLastClickViewId) {
            return true;
        } else {
            sLastClickTime = time;
            sLastClickViewId = viewId;
            return false;
        }
    }




    /**
     * 获取方法的一些辅助信息
     * @param joinPoint
     * @return
     */
    public static String getMethodHelperInfo(final ProceedingJoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        //方法所在类
        Class<?> cls = codeSignature.getDeclaringType();
        //方法名
        String methodName = codeSignature.getName();
        return Utils.getClassName(cls) + "->" + methodName;
    }

    public static String getClassName(Class<?> cls) {
        if (cls.isAnonymousClass()) {
            return getClassName(cls.getEnclosingClass());
        }
        return cls.getSimpleName();
    }

}
