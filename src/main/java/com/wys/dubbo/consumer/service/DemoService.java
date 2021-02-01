package com.wys.dubbo.consumer.service;

import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.DubboDemoServiceClient;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component
public class DemoService {

    @Reference(version = "2.0.0")
    private DubboDemoServiceClient dubboDemoServiceClient;

    public RpcExecuteResult get(){
        RpcExecuteResult rpcExecuteResult = dubboDemoServiceClient.get();
        return rpcExecuteResult;
    }
}
