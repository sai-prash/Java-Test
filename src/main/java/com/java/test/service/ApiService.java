package com.java.test.service;

import java.lang.Thread;

import com.java.test.model.Response;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private int initialValue = 1000;
    private int consumed = 0;
    private int remaining = initialValue - consumed;
    private final Object lock = new Object();

    public void updateInitialValue(int x) {
        synchronized (lock) {
            initialValue = x;
        }
    }
    public void updateRemainingValue(int x) {
        synchronized (lock) {
            remaining = x;
        }
    }

    public void updateConsumedValue(int x) {
        synchronized (lock) {
            consumed = x;
        }
    }


    public Response getConsumedDetails(){
        Response response = new Response();
        response.setTotal(consumed);
        response.setRemaining(initialValue - consumed);
        return response;
    }

    public Response resetQuota(int quota){
        Response response = new Response();
        
        updateInitialValue(quota);
        updateRemainingValue(quota);
        updateConsumedValue(0);

        response.setTotal(consumed);
        response.setRemaining(initialValue);
        return response;
    }

    public Response consumeQuota(int value) throws InterruptedException {
        // Thread.sleep(5000);
        // remaining = remaining - value;
        Response response = new Response();
        if(remaining - value < 0){
            response.setExcess(Math.abs(remaining - value));
            return response;
        }

        updateRemainingValue(remaining - value);
        updateConsumedValue(consumed+value);
        response.setTotal(consumed);
        response.setRemaining(remaining);
        return response;
    }
}
