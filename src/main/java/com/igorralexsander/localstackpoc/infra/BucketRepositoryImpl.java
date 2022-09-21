package com.igorralexsander.localstackpoc.infra;

import com.igorralexsander.localstackpoc.domain.bucket.Bucket;
import com.igorralexsander.localstackpoc.domain.bucket.BucketNotFoundException;
import com.igorralexsander.localstackpoc.domain.bucket.BucketRepository;
import com.igorralexsander.localstackpoc.utils.Constants;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Component
public class BucketRepositoryImpl implements BucketRepository {
    private final S3Client s3Client;
    private final RateLimiter rateLimiter;

    public BucketRepositoryImpl(S3Client s3Client) {
        this.s3Client = s3Client;

        var rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofSeconds(5))
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .build();
        var rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
        this.rateLimiter = rateLimiterRegistry.rateLimiter("s3");

    }

    @Override
    public void createBucket(String name) throws Exception {
        var createRequest = CreateBucketRequest.builder()
                .bucket(name)
                .build();
        var response = s3Client.createBucket(createRequest);
    }

    @Override
    public void deleteBucket(String name) throws Exception {
        var request = DeleteBucketRequest.builder()
                .bucket(name)
                .build();
        try {
            var response = s3Client.deleteBucket(request);
        }catch (NoSuchBucketException e){
            throw BucketNotFoundException.create(name);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Bucket> listBuckets() throws Exception{
        var request = ListBucketsRequest.builder().build();
        var response = s3Client.listBuckets(request);
        return response.buckets().stream()
                .map(x -> new Bucket(x.name(), LocalDateTime.ofInstant(x.creationDate(), Constants.DEFAULT_ZONE_ID)))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listBucketWithDetails() throws Exception {
        for (var i = 0; i<500;i++){
            rateLimitBucket("");
        }
        return Collections.emptyList();
        /*return  listBuckets().stream()
                .map(x -> rateLimitBucket(x.getName()))
                .collect(Collectors.toList());*/
    }

    public String rateLimitBucket(String bucketName) {
        var supplier = RateLimiter.decorateSupplier(rateLimiter, () -> getBucketDetailsT(bucketName));
        return supplier.get();
    }

    public String getBucketDetailsT(String tt){
        System.out.println("Aqui");
        return "teste";
    }

    public String getBucketDetails(String bucketName){
        var request = GetBucketPolicyRequest.builder()
                .bucket(bucketName)
                .build();
        var response = s3Client.getBucketPolicy(request);
        System.out.println(response);
        return response.policy();
    }
}
