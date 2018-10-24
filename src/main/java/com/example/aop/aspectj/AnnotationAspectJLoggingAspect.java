package com.example.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * This Aspect is logging methods from classes marked by annotation @AspectJLoggingAnnotation
 *
 * Is precompiled by aspectj-maven-plugin
 *
 * Pointcuts and Advices with native syntax and @syntax: https://www.eclipse.org/aspectj/doc/next/adk15notebook/ataspectj-pcadvice.html
 *
 * before() and after() advices are commented just to avoid flood
 *
 * Pointcuts can be combined: https://www.eclipse.org/aspectj/doc/next/progguide/printable.html#quick-pointcuts
 *
 * Example:
 * // execution()  ->  ElementType.METHOD
 * // within()     ->  ElementType.TYPE
 * pointcut aspectJLoggingAnnotation():
 *     execution(* *(..)) || within(@com.example.aop.aspectj.AspectJLoggingAnnotation *);
 *
 * Alternative way for @syntax: https://stackoverflow.com/a/2522821/7464024
 */
@Aspect
public class AnnotationAspectJLoggingAspect {
    private static final Logger LOG = Logger.getLogger(AnnotationAspectJLoggingAspect.class.getName());
    private static final String PREFIX = "[AnnotationAspectJLoggingAspect] ";

    // to log ALL methods from stacktrace that are used by methods of class with this annotation:
    // "within(@com.example.aop.aspectj.AspectJLoggingAnnotation *)"
    @Pointcut("execution(* *(..)) && within(@com.example.aop.aspectj.AspectJLoggingAnnotation *)")
    public void aspectJLoggingAnnotationClass(){}

    // @Before("aspectJLoggingAnnotationClass()")
    // public void beforeAdvice() {
    //     LOG.info(PREFIX + "Executing @Before advice");
    // }

    // [WARNING] around on initialization not supported (compiler limitation)
    // [WARNING] around on pre-initialization not supported (compiler limitation)
    @Around("aspectJLoggingAnnotationClass() " +
            "&& !initialization(*.new(..))" +
            "&& !preinitialization(*.new(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            LOG.info(String.format(
                PREFIX + "%s took %d ms",
                joinPoint.getSignature(),
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)
            ));
        }
    }

    // @After("aspectJLoggingAnnotationClass()")
    // public void afterAdvice() {
    //     LOG.info(PREFIX + "Executing @After advice");
    // }
}
