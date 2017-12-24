package com.example.employees;

import com.example.aop.spring.SpringAopConfig;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * From:
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/basic_app_embedded_tomcat/basic_app-tomcat-embedded.html
 *
 * run with debug: https://dzone.com/articles/how-debug-remote-java-applicat
 * java -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n -jar employees-app-1.0-SNAPSHOT-jar-with-dependencies.jar
 *
 * 'suspend=n' is used for not waiting debugger connection to start app
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // Set custom uncaught exception handler and shutdown hook. Just to say goodbye.
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            System.err.println("\n\nthread.getStackTrace():\n" + Arrays.toString(thread.getStackTrace()));
            System.err.println("\n\nexception:\n" + ex.toString());
            System.err.println("\n\nGoodbye from Default Uncaught Exception Handler!\n\n");
        });

        // FYI: Shutdown Hook is called AFTER Default Uncaught Exception Handler
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("\n\nGoodbye from Shutdown Hook!\n\n");
        }));

        // Init Spring context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringAopConfig.class);
        context.refresh();
        // context.refresh(); --> use to get uncaught exception because multiple refresh is not allowed

        // Prepare and run Tomcat instance
        // TODO: https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
        // TODO: Try with tomcat 8: https://github.com/heroku/devcenter-embedded-tomcat/blob/master/src/main/java/launch/Main.java
        // <!--<tomcat.version>8.5.2</tomcat.version>-->
        String contextPath = "";
        String appBase = ".";
        Tomcat tomcat = new Tomcat();

        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp(contextPath, appBase);


        // https://stackoverflow.com/a/17974484/7464024 - custom web.xml support
//        Context webContext = tomcat.addWebapp(contextPath, appBase);
//        URL webXmlUrl = webContext.getClass().getClassLoader().getResource("WEB-INF/web.xml");
//        if (webXmlUrl != null) {
//            System.out.println("webXml path: " + webXmlUrl.getPath());
////            webContext.setAltDDName("/Users/vitaly.arkhipov/projects/my/employees-app/target/classes/WEB-INF/web.xml");
//            webContext.setAltDDName(webXmlUrl.getPath());
//        }

        tomcat.start();
        tomcat.getServer().await();

    }
}
