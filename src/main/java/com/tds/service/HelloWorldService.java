package com.tds.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Simple service.
 * Value: ${server.public.ip} is added to application.yml from Cloudformation script
 *
 */
@Service
@Slf4j
public class HelloWorldService {
    private final AWSService awsService;
    private final String serverPublicIP;

    public HelloWorldService(AWSService awsService,
                             @Value("${server.public.ip:0.0.0.0}") String serverPublicIP) {
        this.awsService = awsService;
        this.serverPublicIP = serverPublicIP;
    }

    public String getHello() {
        final String lcalhostAddress;
        try {
            final InetAddress inetAddress = InetAddress.getLocalHost();
            lcalhostAddress = inetAddress.getHostAddress();
            log.info("Server Public IP Address: {}", serverPublicIP);
            log.info("Localhost IP Address: {}", lcalhostAddress);
            log.info("Hostname: {}", inetAddress.getHostName());
        }catch(UnknownHostException unknownHostException){
            throw new RuntimeException("Could not resolve host address", unknownHostException);
        }

        return String.format("Hello from <%s>", serverPublicIP);
    }

    public byte[] getPictFromResources() {
        log.info("reading file logo.jpg from resources");

        byte[] bytes;
        ClassPathResource classPathResource = new ClassPathResource("logo.jpg");

        try {
            bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("could not read file from resource: ", e);
        }

        return bytes;
    }

    public byte[] getS3Pict() {
        return awsService.getS3Pict();
    }

    public String getHtml() {
        String s3FileUrl = awsService.getPresignedUrl().toString();
        String hello = getHello();
        String responseTemplate =
            "<p id=\"hello\">" + hello + "</p>\n" +
            "<img src=\"" + s3FileUrl + "\" alt=\"logo\" />\n";
        return responseTemplate;
    }
}
