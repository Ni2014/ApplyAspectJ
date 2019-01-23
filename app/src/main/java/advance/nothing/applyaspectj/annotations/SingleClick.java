package advance.nothing.applyaspectj.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 按钮点击防抖(防止多次点击)
 * 让用户自定义时间阀值 默认1000毫秒
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {

    long DEFAULT_INTERVAL_MILLIS = 1000;

    long value() default DEFAULT_INTERVAL_MILLIS;
}
