package com.wys.dubbo.consumer.service;

import com.alibaba.fastjson.JSON;
import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.DubboDemoServiceClient;
import com.wys.dubbo.service.LoadBalanceDemoServiceClient;
import com.wys.dubbo.service.MockDemoServiceClient;
import com.wys.dubbo.service.ProtocolDemoServiceClient;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

@Component
public class DemoService {

    @DubboReference(version = "2.0.0")
    private DubboDemoServiceClient dubboDemoServiceClient2;

    @DubboReference(version = "1.0.0",methods = {@Method(name="get",async = true)},timeout = 10000)
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
        long start = System.currentTimeMillis();
        RpcExecuteResult rpcExecuteResult = dubboDemoServiceClient1.get();
        long end = System.currentTimeMillis();
        System.out.println("消费者异步调用后端服务所用时间:"+(end-start));
        return rpcExecuteResult;
    }
    public RpcExecuteResult get3(){
        long start = System.currentTimeMillis();
        RpcExecuteResult rpcExecuteResult = dubboDemoServiceClient1.get();
        System.out.println("调用结果1："+ JSON.toJSON(rpcExecuteResult));
        Future<RpcExecuteResult> future = RpcContext.getContext().getFuture();
        try {
            rpcExecuteResult = future.get();
            System.out.println("调用结果2："+ JSON.toJSON(rpcExecuteResult));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("消费者异步调用后端服务所用时间:"+(end-start));
        return rpcExecuteResult;
    }
    public RpcExecuteResult get2(){
        RpcExecuteResult rpcExecuteResult = dubboDemoServiceClient2.get();
        return rpcExecuteResult;
    }
    public RpcExecuteResult asyncGet(){
        long start = System.currentTimeMillis();
        CompletableFuture<RpcExecuteResult> completableFuture = dubboDemoServiceClient1.asyncGet();
        completableFuture.whenComplete(new BiConsumer<RpcExecuteResult, Throwable>() {
            @Override
            public void accept(RpcExecuteResult rpcExecuteResult, Throwable throwable) {
                if(throwable!=null){
                    throwable.printStackTrace();
                }else{
                    System.out.println("异步返回结果:"+JSON.toJSON(rpcExecuteResult));
                }
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("消费者异步调用后端服务所用时间:"+(end-start));
        return null;
    }
    public void get4(){
        long start = System.currentTimeMillis();
        CompletableFuture<RpcExecuteResult> completableFuture = dubboDemoServiceClient1.get2();
        completableFuture.whenComplete(new BiConsumer<RpcExecuteResult, Throwable>() {
            @Override
            public void accept(RpcExecuteResult rpcExecuteResult, Throwable throwable) {
                if(throwable!=null){
                    throwable.printStackTrace();
                }else{
                    System.out.println("异步返回结果:"+JSON.toJSON(rpcExecuteResult));
                }
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("消费者异步调用后端服务所用时间:"+(end-start));
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
