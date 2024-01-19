package com.tds.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloWorldServiceTest {
    private HelloWorldService underTest;

    @Mock
    private AWSService awsService;

    @BeforeEach
    void setUp() {
        underTest = new HelloWorldService(awsService, "127.0.0.1");
    }

    @Test
    void getHtml() throws MalformedURLException {
        String expectedResponse = "<p id=\"hello\">Hello from <127.0.0.1></p>\n" +
                "<img src=\"https://test-bucket.s3.erth.amazonaws.com/test.jpg?X-Amz-Security-Token=IQgBEAiBK7%2FiYy&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20270452Z&X-Amz-SignedHeaders=host&X-Amz-Expires=60&X-Amz-Credential=40210%2Feu-earth&X-Amz-Signature=850\" alt=\"logo\" />\n";
        String presignedPath = "/test.jpg?X-Amz-Security-Token=IQgBEAiBK7%2FiYy&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20270452Z&X-Amz-SignedHeaders=host&X-Amz-Expires=60&X-Amz-Credential=40210%2Feu-earth&X-Amz-Signature=850";
        URL domainUrl = new URL("https://test-bucket.s3.erth.amazonaws.com");
        URL presignedUrl = new URL(domainUrl + presignedPath);
        when(awsService.getPresignedUrl()).thenReturn(presignedUrl);

        String result = underTest.getHtml();

        assertEquals(expectedResponse, result);
    }
}