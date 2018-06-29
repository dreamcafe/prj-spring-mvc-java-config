package org.dreamcafe.smjc.server;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

@Component
@Slf4j
public class EmbeddedTomcat {

    @Value("${server.port:}")
    private String portStr;

    @Value("${server.webapps.path:}")
    private String webappsPathStr;

    private Tomcat tomcat;

    @PostConstruct
    public void startUp() {
        try {
            Integer port = null;
            if (StringUtils.isBlank(portStr)) {
                port = Integer.valueOf(8080);
            } else {
                port = Integer.valueOf(portStr);
            }

            String finalWebappsPathStr = null;
            if (StringUtils.isBlank(webappsPathStr)) {
                finalWebappsPathStr = "src/main/resources/webapps/";
            } else {
                finalWebappsPathStr = webappsPathStr;
            }

            tomcat = new Tomcat();
            String baseDirStr = createTempDir(port);
            log.info("Add base directory: {}", baseDirStr);
            tomcat.setBaseDir(baseDirStr);

            String webAppDirStr = Paths.get("./", finalWebappsPathStr).toFile().getCanonicalPath();
            log.info("Add web app directory: {}", webAppDirStr);
            tomcat.addWebapp("", webAppDirStr);

            Connector connector = new Connector(Http11Nio2Protocol.class.getName());
            // Listen only on localhost
            connector.setAttribute("address",
                    InetAddress.getByName("localhost").getHostAddress());
            // Use specific port
            connector.setPort(port);
            // Mainly set to reduce timeouts during async tests
            connector.setAttribute("connectionTimeout", "3000");
            tomcat.getService().addConnector(connector);
            tomcat.setConnector(connector);

            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | IOException | LifecycleException ex) {
            throw new IllegalStateException("Failed to start tomcat instance.", ex);
        }
    }

    @PreDestroy
    public void shutDown() {
        if (null != tomcat) {
            try {
                tomcat.stop();
            } catch (LifecycleException ex) {
                log.warn("Some errors occurred when tomcat was shutting down.", ex);
            }
        }
    }

    private String createTempDir(int port) {
        try {
            SecureRandom random = new SecureRandom();
            StringBuilder tempDirName = new StringBuilder(30);
            tempDirName.append("tomcat").append(".").append(Math.abs(random.nextLong())).append(".").append(port);
            tempDirName.trimToSize();

            String parentTempDirName = System.getProperty("java.io.tmpdir");

            Path tempDirPath = Paths.get(parentTempDirName, tempDirName.toString());
            if (Files.exists(tempDirPath)) {
                Files.delete(tempDirPath);
            }

            Files.createDirectory(tempDirPath);

            File tempDirFile = tempDirPath.toFile();
            tempDirFile.deleteOnExit();
            return tempDirFile.getCanonicalPath();
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to create temp directory.", ex);
        }
    }
}
