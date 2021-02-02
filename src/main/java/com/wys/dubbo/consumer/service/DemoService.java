package com.wys.dubbo.consumer.service;

import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.DubboDemoServiceClient;
import com.wys.dubbo.service.LoadBalanceDemoServiceClient;
import com.wys.dubbo.service.MockDemoServiceClient;
import com.wys.dubbo.service.ProtocolDemoServiceClient;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component
public class DemoService {

    @DubboReference(version = "2.0.0")
    private DubboDemoServiceClient dubboDemoServiceClient2;

    @DubboReference(version = "1.0.0")
    private DubboDemoServiceClient dubboDemoServiceClient1;

    @DubboReference(id = "weixin",group = "pay.weixin")
    private DubboDemoServiceClient weixinServiceClient;

    @DubboReference(id = "zhifubao",group = "pay.zhifubao")
    private DubboDemoServiceClient zhifubaoServiceClient;

    @DubboReference(protocol = "rmi")
    private ProtocolDemoServiceClient protocolDemoServiceClient;

    @DubboReference(loadbalance = "random")
    private LoadBalanceDemoServiceClient loadBalanceDemoServiceClient;

    @DubboReference(mock = "true",timeout = 3000)
    private MockDemoServiceClient mockDemoServiceClient;

    public RpcExecuteResult get1(){
        RpcExecuteResult rpcExecuteResult = dubboDemoServiceClient1.get();
        return rpcExecuteResult;
    }
    public RpcExecuteResult get2(){
        RpcExecuteResult rpcExecuteResult = dubboDemoServiceClient2.get();
        return rpcExecuteResult;
    }
    public RpcExecuteResult weixin(){
        RpcExecuteResult rpcExecuteResult = weixinServiceClient.get();
        return rpcExecuteResult;
    }
    public RpcExecuteResult zhifubao(){
        RpcExecuteResult rpcExecuteResult = zhifubaoServiceClient.get();
        return rpcExecuteResult;
    }
    public RpcExecuteResult protocol(){
        RpcExecuteResult rpcExecuteResult = protocolDemoServiceClient.get();
        return rpcExecuteResult;
    }

    public RpcExecuteResult loadBalance(){
        RpcExecuteResult rpcExecuteResult = loadBalanceDemoServiceClient.get();
        return rpcExecuteResult;
    }
    public RpcExecuteResult mock(){
        RpcExecuteResult rpcExecuteResult = mockDemoServiceClient.get();
        return rpcExecuteResult;
    }
}
