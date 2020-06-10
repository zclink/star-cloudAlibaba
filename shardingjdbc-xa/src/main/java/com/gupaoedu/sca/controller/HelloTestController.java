package com.gupaoedu.sca.controller;

import com.gupaoedu.sca.service.OrderService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTestController {


    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    @ShardingTransactionType(value = TransactionType.XA)
    @Transactional(rollbackFor = Exception.class)
    public String test() {

        orderService.insertOne(333);

//        int a = 1/0;

        orderService.insertTwo(666);

        orderService.insertUser("aaa");

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
        int b = 1/0;

        return "hello world";
    }


}
