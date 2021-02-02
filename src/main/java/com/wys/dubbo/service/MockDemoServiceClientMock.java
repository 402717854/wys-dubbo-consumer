package com.wys.dubbo.service;

import com.wys.dubbo.result.RpcExecuteResult;
import com.wys.dubbo.service.MockDemoServiceClient;

public class MockDemoServiceClientMock implements MockDemoServiceClient {
    @Override
    public RpcExecuteResult get() {
        return RpcExecuteResult.fail("降级处理");
    }
}
