package com.project.StudentHub.configuration.email;

import org.springframework.stereotype.Component;

@Component
public class EmailConfiguration {
    @org.springframework.beans.factory.annotation.Value("${spring.mail.host}")
    private String host;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.port}")
    private int port;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
    private String username;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.password}")
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
