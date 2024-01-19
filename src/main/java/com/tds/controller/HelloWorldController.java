package com.tds.controller;

import com.tds.service.HelloWorldService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private final HelloWorldService helloWorldService;

    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping
    public ResponseEntity<String> helloWorld() {
        HttpHeaders responseHeaders = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(helloWorldService.getHello());
    }

    @GetMapping(value = "pict")
    public ResponseEntity<byte[]> getLocalPict() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(helloWorldService.getPictFromResources());
    }

    @GetMapping(value = "pict_s3")
    public ResponseEntity<byte[]> getS3Pict() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(helloWorldService.getS3Pict());
    }

    @GetMapping(value = "html")
    public ResponseEntity<String> getHtml() {
        HttpHeaders responseHeaders = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(helloWorldService.getHtml());
    }

    @GetMapping("crash")
    public void crash() {
        Runtime.getRuntime().halt(55);
    }

}
