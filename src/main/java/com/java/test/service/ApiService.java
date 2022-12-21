package com.java.test.service;

import java.lang.Thread;

import com.java.test.model.Response;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private static int initialValue = 1000;
    private static int consumed = 0;
    private static int remaining = initialValue - consumed;
    // private final Object lock = new Object();

    public Response getConsumedDetails(){
        Response response = new Response();
        response.setTotal(consumed);
        response.setRemaining(initialValue - consumed);
        return response;
    }

    public Response resetQuota(int quota){
        Response response = new Response();
        initialValue = remaining = quota;
        consumed = 0;
        response.setTotal(consumed);
        response.setRemaining(initialValue);
        return response;
    }

    public Response consumeQuota(int value) {
        
        Response response = new Response();
        if(remaining - value < 0){
            int excess = Math.abs(remaining - value);
            response.setExcess(excess);
            return response;
        }
        remaining = remaining - value;
        consumed +=value;
        response.setTotal(consumed);
        response.setRemaining(remaining);
        return response;
    }
}
