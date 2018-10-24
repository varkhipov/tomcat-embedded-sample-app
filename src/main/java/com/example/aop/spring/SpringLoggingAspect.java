package com.example.aop.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * This Aspect is logging "public void test()" method
 *
 * before() and after() advices are commented just to avoid flood
 *
 * May be useful:
 * https://www.javacodegeeks.com/2012/06/simple-introduction-to-aop.html
 *
 * TODO:
 * try this approach: https://www.concretepage.com/spring/stopwatch-spring-example
 */

@Aspect
@Component
public class SpringLoggingAspect {
    private static final Logger LOG = Logger.getLogger(SpringLoggingAspect.class.getName());
    private static final String PREFIX = "[SpringLoggingAspect] ";

    @Pointcut("execution(public void test())")
    public void test(){}

    // @Before("test()")
    // public void beforeAdvice() {
    //     LOG.info(PREFIX + "Executing @Before advice");
    // }

    @Around("test()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        // why nano? because why not: https://stackoverflow.com/a/351571/7464024
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            // ns to ms: https://stackoverflow.com/a/21600253/7464024
            // equivalent: TimeUnit.MILLISECONDS.convert(delayNS, TimeUnit.NANOSECONDS)
            LOG.info(String.format(
                PREFIX + "%s took %d ms",
                joinPoint.getSignature(),
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)
            ));
        }
    }

    // @After("test()")
    // public void afterAdvice() {
    //     LOG.info(PREFIX + "Executing @After advice");
    // }
}
