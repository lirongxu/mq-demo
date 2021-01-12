package com.lemon.mq.kafka.demo.controller;

import com.lemon.mq.kafka.demo.entity.Result;
import com.lemon.mq.kafka.demo.producer.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author 李荣许
 * @create 2020/12/24
 */
@RequestMapping("/v1")
@RestController
public class MessageController {
    @Resource
    private MessageService messageService;

    @GetMapping("/simple/send")
    public Result workSend(@RequestParam String message)  {
        messageService.sendMessage(message);

        return Result.ok();
    }
}
