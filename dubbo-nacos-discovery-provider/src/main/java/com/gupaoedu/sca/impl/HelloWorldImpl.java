package com.gupaoedu.sca.impl;

import com.gupaoedu.sca.service.HelloWorldService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloWorldImpl implements HelloWorldService {

    @Override
    public String helloWorld(String msg) {
        return "hellow world : " + msg;
    }

}
