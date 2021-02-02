package com.wys.dubbo.consumer.controller;

import com.wys.dubbo.consumer.common.annotation.ResponseResult;
import com.wys.dubbo.consumer.service.DemoService;
import com.wys.dubbo.result.RpcExecuteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@ResponseResult
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/get1")
    public RpcExecuteResult get1(){
        return demoService.get1();
    }
    @GetMapping("/get2")
    public RpcExecuteResult get2(){
        return demoService.get2();
    }
    @GetMapping("/weixin")
    public RpcExecuteResult weixin(){
        return demoService.weixin();
    }
    @GetMapping("/zhifubao")
    public RpcExecuteResult zhifubao(){
        return demoService.zhifubao();
    }
    @GetMapping("/protocol")
    public RpcExecuteResult protocol(){
        return demoService.protocol();
    }
    @GetMapping("/loadBalance")
    public RpcExecuteResult loadBalance(){
        return demoService.loadBalance();
    }

    @GetMapping("/mock")
    public RpcExecuteResult mock(){
        return demoService.mock();
    }
}
