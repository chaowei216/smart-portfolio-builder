package com.weiz.spb.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Slf4j
public class DefaultAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, @NonNull Object... params) {
        log.info("Async Error in: {} , with error: {}", method.getName(), ex.getCause());
    }
}
