package com.tds.service;

import io.awspring.cloud.s3.S3Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

/**
 * Simple AWS service to get one file from S3 bucket and prepare Presigned URL with expiration for the file.
 * Values: ${aws.bucket.name} and ${aws.bucket.key} are added to application.yml from Cloudformation script
 *
 */
@Service
@Slf4j
public class AWSService {
    private final S3Template s3Template;
    private final String bucketName;
    private final String bucketKey;
    private final Resource s3Resource;
    private final long urlExpirationMin;

    public AWSService(S3Template s3Template,
                      @Value("s3://${aws.bucket.name}/${aws.bucket.key}") Resource s3Resource,
                      @Value("${aws.bucket.name}") String bucketName,
                      @Value("${aws.bucket.key}") String bucketKey,
                      @Value("${aws.bucket.urlExpirationMin}") long urlExpirationMin) {
        this.s3Template = s3Template;
        this.bucketName = bucketName;
        this.bucketKey = bucketKey;
        this.s3Resource = s3Resource;
        this.urlExpirationMin = urlExpirationMin;
    }

    public byte[] getS3Pict() {
        log.info("reading file {} from S3 bucket {}", bucketKey, bucketName);
        byte[] bytes;

        try (InputStream inputStream = s3Resource.getInputStream()) {
            bytes = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(String.format("could not read file %s from bucket %s. ", bucketKey, bucketName), e);
        }

        return bytes;
    }

    public URL getPresignedUrl() {
        log.info("preparing presigned url for S3 bucket/file: {}/{}", bucketName, bucketKey);
        return s3Template.createSignedGetURL(bucketName, bucketKey, Duration.ofMinutes(urlExpirationMin));
    }
}
