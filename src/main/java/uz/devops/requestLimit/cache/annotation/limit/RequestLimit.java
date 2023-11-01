package uz.devops.requestLimit.cache.annotation.limit;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestLimit {
    String[] keys() default {};
    long timeToLive() default 0L;
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    String paramName() default "";
}
