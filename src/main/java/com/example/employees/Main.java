package com.example.employees;

import org.apache.catalina.startup.Tomcat;

import java.util.Optional;

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
    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));

    public static void main(String[] args) throws Exception {
        String contextPath = "/";
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(port.orElse("8080") ));
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
