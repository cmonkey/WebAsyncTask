package com.ifdp.webTask.controller;

import com.ifdp.webTask.service.WebAsyncTaskService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class WebAsyncTaskController {

    private final static String ERROR_MESSAGE = "task error";
    private final static String timeout_message = "task timeout";

    @Resource
    WebAsyncTaskService webAsyncTaskService;


}
