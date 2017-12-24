package com.example.aop.aspectj;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * This Aspect is logging methods marked by annotation @AspectJLoggingAnnotation
 *
 * Is precompiled by aspectj-maven-plugin
 *
 * Pointcuts and Advices with native syntax and @syntax: https://www.eclipse.org/aspectj/doc/next/adk15notebook/ataspectj-pcadvice.html
 *
 * Advices are sensitive to order: https://stackoverflow.com/a/40071281/7464024
 * before() and after() advices are commented just to avoid flood
 *
 * Pointcuts can be combined: https://www.eclipse.org/aspectj/doc/next/progguide/printable.html#quick-pointcuts
 *
 * Example:
 * // execution()  ->  ElementType.METHOD
 * // within()     ->  ElementType.TYPE
 * pointcut aspectJLoggingAnnotation():
 *     execution(* *(..)) || within(@com.example.aop.aspectj.AspectJLoggingAnnotation *);
 */
public aspect NativeAspectJLoggingAspect {
    private static final Logger LOG = Logger.getLogger(NativeAspectJLoggingAspect.class.getName());
    private static final String PREFIX = "[NativeAspectJLoggingAspect] ";

    pointcut aspectJLoggingAnnotationMethod(): execution(@com.example.aop.aspectj.AspectJLoggingAnnotation * *(..));

    // before(): aspectJLoggingAnnotationMethod() {
    //     LOG.info(PREFIX + "Executing @Before advice");
    // }

    // [WARNING] around on initialization not supported (compiler limitation)
    // [WARNING] around on pre-initialization not supported (compiler limitation)
    Object around(): aspectJLoggingAnnotationMethod()
                     && !initialization(*.new(..))
                     && !preinitialization(*.new(..)) {

        long start = System.nanoTime();

        Object result = proceed();

        LOG.info(String.format(
            PREFIX + "%s took %d ms",
            thisJoinPointStaticPart.getSignature(),
            TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)
        ));

        return result;
    }

    // after(): aspectJLoggingAnnotationMethod() {
    //     LOG.info(PREFIX + "Executing @After advice");
    // }
}
