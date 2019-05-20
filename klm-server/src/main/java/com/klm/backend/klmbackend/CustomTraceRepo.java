package com.klm.backend.klmbackend;

import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomTraceRepo extends InMemoryHttpTraceRepository {

    public CustomTraceRepo() {
        super.setCapacity(1000);
    }
 
}
