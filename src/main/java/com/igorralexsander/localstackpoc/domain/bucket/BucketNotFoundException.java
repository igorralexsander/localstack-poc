package com.igorralexsander.localstackpoc.domain.bucket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BucketNotFoundException extends Exception{
    public static BucketNotFoundException create(String bucketName){
        return new BucketNotFoundException(bucketName);
    }
    public BucketNotFoundException() {

    }

    public BucketNotFoundException(String bucketName) {
        super(String.format("Bucket with name %s does not exists.", bucketName));
    }
}
