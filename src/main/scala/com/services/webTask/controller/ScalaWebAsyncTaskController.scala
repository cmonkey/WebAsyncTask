package com.services.webTask.controller

import com.services.webTask.service.WebAsyncTaskService
import javax.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.bind.annotation.{GetMapping, RestController}
import org.springframework.web.context.request.async.WebAsyncTask

@RestController
class ScalaWebAsyncTaskController {
  val logger = LoggerFactory.getLogger(classOf[ScalaWebAsyncTaskController])

  @Resource(name = "taskExecutor")
  implicit val taskExecutor: ThreadPoolTaskExecutor = null

  @Resource
  val webAsyncTaskService: WebAsyncTaskService = null

  @GetMapping(Array("/scalaThreadPool"))
  def scalaAsyncTaskThreadPool() = {
    new WebAsyncTask[String](10 * 1000L, () => {
      logger.info("currentThread name = {}", Thread.currentThread().getName())

      webAsyncTaskService.getId
    })
  }

}
