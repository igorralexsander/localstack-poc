package com.igorralexsander.localstackpoc.domain.bucket;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BucketService {
    private final BucketRepository bucketRepository;

    public BucketService(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    public void createBucket(BucketInput bucketInput) throws Exception{
        bucketRepository.createBucket(bucketInput.getName());

    }

    public void deleteBucket(BucketInput bucketInput) throws Exception{
        bucketRepository.deleteBucket(bucketInput.getName());
    }

    public List<Bucket> getAllBuckets() throws Exception{
        return bucketRepository.listBuckets();
    }

    public List<String> listBucketsWithDetails() throws Exception {
        return bucketRepository.listBucketWithDetails();
    }

}
