//package com.test;
//
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.env.EnvironmentPostProcessor;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MockWebServerEnvironmentPostProcessor
//        implements EnvironmentPostProcessor, DisposableBean {
//
//    private final MockWebServerPropertySource propertySource = new MockWebServerPropertySource();
//
//    @Override
//    public void postProcessEnvironment(ConfigurableEnvironment environment,
//                                       SpringApplication application) {
//        environment.getPropertySources().addFirst(this.propertySource);
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        this.propertySource.destroy();
//    }
//}