package com.igorralexsander.localstackpoc.domain.bucket;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BucketRepository {

    void createBucket(String name) throws Exception;
    void deleteBucket(String name) throws Exception;
    List<Bucket> listBuckets() throws Exception;

    List<String> listBucketWithDetails() throws Exception;
}
