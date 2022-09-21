package com.igorralexsander.localstackpoc.domain.bucket;

import java.time.LocalDateTime;

public class Bucket {
    private String name;

    public Bucket(String name, LocalDateTime creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    private LocalDateTime creationDate;



    public Bucket() {
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
