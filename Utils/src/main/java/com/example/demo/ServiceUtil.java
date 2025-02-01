package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import  org.slf4j.Logger;

@Component
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ServiceUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceUtil.class);
    private String serviceAddress;

    @Value("${server.port}")
    private final String port;

    public String getPort() {
        return port;
    }

    public String retrieveServiceAddress() {
        if (serviceAddress == null) {
            serviceAddress = getHostName() + "/" + getIpAddress() + ":" + port;
        }
        return serviceAddress;
    }

    private String getHostName() {
        try {
            //Nom de la machine
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            LOG.error("Failed to retrieve hostname", e);
            return "unknown-host";
        }
    }

    private String getIpAddress() {
        try {
            //addresse du serveur locale localhost/127.0.0.1
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.error("Failed to retrieve IP address", e);
            return "unknown-ip";
        }
    }

}
