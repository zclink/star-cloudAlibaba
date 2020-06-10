package com.gupaoedu.sca.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//使用@Value注解赋值给变量了据需要加@RefreshScope 来保证变量可以跟配置一起正常刷新
@RefreshScope
public class HelloConfigController {

    @Value("${info:i am default info}")
    private String info;

    @Value("${username: i am default name}")
    private String name;

    @GetMapping("/test")
    public String test() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("info",info);
        jsonObject.put("name",name);
        return jsonObject.toJSONString();
    }
}
