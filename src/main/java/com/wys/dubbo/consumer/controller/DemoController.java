package com.wys.dubbo.consumer.controller;

import com.wys.dubbo.consumer.service.DemoService;
import com.wys.dubbo.result.RpcExecuteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/get")
    public RpcExecuteResult get(){
        return demoService.get();
    }
}
