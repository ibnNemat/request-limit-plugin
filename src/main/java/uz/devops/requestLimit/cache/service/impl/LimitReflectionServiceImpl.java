package uz.devops.requestLimit.cache.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import uz.devops.requestLimit.cache.annotation.limit.RequestLimit;
import uz.devops.requestLimit.cache.service.LimitReflectionService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Service
@Slf4j
@AllArgsConstructor
public class LimitReflectionServiceImpl implements LimitReflectionService {

    @Override
    public Object getField(String fieldName, Class<?> requestClass, Object requestData) throws IllegalAccessException, NoSuchFieldException {
        Field field;
        try {
            field = requestClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            var superClass = requestClass.getSuperclass();
            if (superClass == null) {
                log.warn("Field not found with name: {}", fieldName);
                return null;
            }
            field = superClass.getDeclaredField(fieldName);
        }

        field.setAccessible(true);
        return field.get(requestData);
    }

    @Override
    @SneakyThrows
    public RequestLimit getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = joinPoint.getTarget()
                .getClass()
                .getMethod(signature.getName(), signature.getParameterTypes());

        return method.getAnnotation(RequestLimit.class);
    }
}
