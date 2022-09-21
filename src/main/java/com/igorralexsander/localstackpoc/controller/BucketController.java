package com.igorralexsander.localstackpoc.controller;

import com.igorralexsander.localstackpoc.domain.bucket.Bucket;
import com.igorralexsander.localstackpoc.domain.bucket.BucketInput;
import com.igorralexsander.localstackpoc.domain.bucket.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="bucket")
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping
    public ResponseEntity<Void> createBucket(@RequestBody BucketInput bucket){
        try{
            bucketService.createBucket(bucket);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Bucket>> getAllBuckets(){
        try{
            var buckets = bucketService.getAllBuckets();
            return ResponseEntity.ok(buckets);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBucket(@RequestBody BucketInput bucketInput) throws Exception {
        bucketService.deleteBucket(bucketInput);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/details")
    public ResponseEntity<List<String>> listBucketWithDetails(){
        try{
            var result = bucketService.listBucketsWithDetails();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
