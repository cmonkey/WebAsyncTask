package com.services.webTask.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WebAsyncTaskService {

    public String getId(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
