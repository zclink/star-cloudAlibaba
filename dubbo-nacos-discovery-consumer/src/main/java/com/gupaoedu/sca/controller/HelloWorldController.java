package com.gupaoedu.sca.controller;

import com.gupaoedu.sca.client.DubboClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private DubboClient dubboClient;

    @GetMapping("/helloworld")
    public String hellowWorld(@RequestParam String msg) {
        return dubboClient.helloWorld(msg);
    }


}
