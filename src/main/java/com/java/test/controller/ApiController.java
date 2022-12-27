package com.java.test.controller;

import com.java.test.model.Request;
import com.java.test.model.Response;
import com.java.test.service.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ApiController {

    private ApiService service;

    @GetMapping("/consume")
    private ResponseEntity<Response> getConsumedDetails(){
        return new ResponseEntity<>(service.getConsumedDetails(), HttpStatus.OK);
    }

    @PostMapping("/reset")
    private ResponseEntity<Response> resetQuota(@RequestBody Request request){
        return new ResponseEntity<>(service.resetQuota(request.getQuota()), HttpStatus.OK);
    }

    @PostMapping("/consume")
    private ResponseEntity<Response> consumeQuota(@RequestBody Request request){
        Response response = service.consumeQuota(request.getValue());
       
        if(response.getExcess() != null){
            return new ResponseEntity<>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
