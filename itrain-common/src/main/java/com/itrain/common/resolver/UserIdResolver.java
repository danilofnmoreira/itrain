package com.itrain.common.resolver;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return findMethodAnnotation(UserId.class, parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        try {

            var principal = webRequest.getUserPrincipal();
            var stringUserId = principal.getName();
            return Long.valueOf(stringUserId);

        } catch (Exception e) {

            var userIdAnnotation = findMethodAnnotation(UserId.class, parameter);

            Objects.requireNonNull(userIdAnnotation);

            if (userIdAnnotation.nullable()) {
                return null;
            }

            throw e;
        }

    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {

        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }

        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }

        return null;
    }

    @Target(value = { ElementType.PARAMETER })
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface UserId {

        boolean nullable() default false;
    }

}