package com.allinabc.cloud.common.api.strategy;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.Callable;

/**
 * Feign Hystrix微服务Header传播
 * https://blog.csdn.net/weihao_/article/details/83240099
 */
@Component
public class HystrixConcurrencyStrategyCustomize extends HystrixConcurrencyStrategy {

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return new HystrixCustomizeCallable(attributes, callable);
    }

    class HystrixCustomizeCallable<T> implements Callable<T> {

        private ServletRequestAttributes attributes;

        private Callable<T> callable;

        public HystrixCustomizeCallable(ServletRequestAttributes attributes, Callable<T> callable) {
            this.attributes = attributes;
            this.callable = callable;
        }

        @Override
        public T call() throws Exception {
            try {
                if (null != this.attributes) {
                    RequestContextHolder.setRequestAttributes(this.attributes);
                }
                return this.callable.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }

}
