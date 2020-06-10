package com.gupaoedu.sca.controller;

import com.gupaoedu.sca.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTestController {


    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String test() {

        orderService.insertOne(333);
        orderService.insertTwo(666);

        return "hello world";
    }


}
