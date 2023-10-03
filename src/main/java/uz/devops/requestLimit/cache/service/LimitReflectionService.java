package uz.devops.requestLimit.cache.service;

import org.aspectj.lang.JoinPoint;
import uz.devops.requestLimit.cache.annotation.limit.RequestLimit;

public interface LimitReflectionService {
    Object getField(String fieldName, Class<?> requestClass, Object requestData) throws IllegalAccessException, NoSuchFieldException;
    RequestLimit getAnnotation(JoinPoint joinPoint);
}
