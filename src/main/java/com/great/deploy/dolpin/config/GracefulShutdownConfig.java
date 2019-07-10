package com.great.deploy.dolpin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class GracefulShutdownConfig implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    ApplicationContext context;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        ThreadPoolTaskExecutor threadPool = (ThreadPoolTaskExecutor) context.getBean("threadPoolTaskExecutor");
        threadPool.setAwaitTerminationSeconds(30);
    }
}
