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
    public boolean supportsParameter(final MethodParameter parameter) {

        return findMethodAnnotation(UserId.class, parameter) != null;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {

        try {

            final var principal = webRequest.getUserPrincipal();
            final var stringUserId = principal.getName();
            return Long.valueOf(stringUserId);

        } catch (final Exception e) {

            final var userIdAnnotation = findMethodAnnotation(UserId.class, parameter);

            Objects.requireNonNull(userIdAnnotation);

            if (userIdAnnotation.nullable()) {
                return null;
            }

            throw e;
        }

    }

    private <T extends Annotation> T findMethodAnnotation(final Class<T> annotationClass, final MethodParameter parameter) {

        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }

        final Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (final Annotation toSearch : annotationsToSearch) {
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