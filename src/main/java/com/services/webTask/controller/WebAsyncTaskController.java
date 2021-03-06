package com.services.webTask.controller;

import com.services.webTask.service.WebAsyncTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

@RestController
public class WebAsyncTaskController {
    private final static Logger logger = LoggerFactory.getLogger(WebAsyncTaskController.class);

    private final static String ERROR_MESSAGE = "task error";
    private final static String TIMEOUT_MESSAGE = "task timeout";

    @Resource
    WebAsyncTaskService webAsyncTaskService;

    @Resource(name = "taskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/completion")
    public WebAsyncTask<String> asyncTaskCompletion(){
        logger.info("currentThread name = {}", currentThread().getName());

        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(10 * 1000,() -> {
            logger.info("aysnc thread = {}", currentThread().getName());

            sleep(5 * 1000L);

            return webAsyncTaskService.getId();
        });

        webAsyncTask.onCompletion(() -> logger.info("task finish"));

        logger.info("completion end");

        return webAsyncTask;

    }

    @GetMapping("/exception")
    public WebAsyncTask<String> asyncTaskException(){
        logger.info("currentThread name = {}", currentThread().getName());

        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(10 * 1000, () -> {
            logger.info("aysnc thread = {}", currentThread().getName());

            sleep(5 * 1000L);

            throw new Exception(ERROR_MESSAGE);
        });

        webAsyncTask.onCompletion(() -> logger.info("task finish"));
        webAsyncTask.onError(() -> {
            logger.info("task run exception");
            return ERROR_MESSAGE;
        });

        logger.info("Exception end");

        return webAsyncTask;
    }

    @GetMapping("/timeout")
    public WebAsyncTask<String> asyncTaskTimeout(){
        logger.info("currentThread name = {}", currentThread().getName());

        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(10 * 1000, () -> {
            logger.info("aysnc thread = {}", currentThread().getName());

            sleep(15 * 1000L);

            return TIMEOUT_MESSAGE;
        });

        webAsyncTask.onCompletion(() -> logger.info("task finish"));
        webAsyncTask.onTimeout(() -> {
            logger.info("task run timeout");
            return TIMEOUT_MESSAGE;
        });

        logger.info("timeout end");

        return webAsyncTask;
    }

    @GetMapping("/threadPool")
    public WebAsyncTask<String> threadPool(){
        return new WebAsyncTask<>(10 * 1000L, threadPoolTaskExecutor, () -> {
            logger.info("thread Pool thread name = {}", currentThread().getName());

            return webAsyncTaskService.getId();
        });
    }
}
