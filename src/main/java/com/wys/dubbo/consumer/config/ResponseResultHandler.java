package com.wys.dubbo.consumer.config;

import com.wys.dubbo.consumer.common.annotation.ResponseResult;
import com.wys.dubbo.consumer.common.response.ExecuteResult;
import com.wys.dubbo.result.RpcExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ResponseResultHandler
 * @Description: TODO controller注解@ResponseResult返回值统一处理处理
 * @Author 86133
 * @Date 2021/01/27
 **/
@Slf4j
@RestControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice {

    private static final String RESPONSE_RESULT_ANN="RESPONSE_RESULT_ANN";

    //判断请求是否包含了包装注解标记，没有就直接返回，不需要重写返回体
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);
        return responseResult==null?false:true;
   }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(body instanceof ExecuteResult){
            return body;
        }
        if(body instanceof RpcExecuteResult){
            RpcExecuteResult rpcExecuteResult=(RpcExecuteResult)body;
            if(rpcExecuteResult.isSuccess()){
               return ExecuteResult.ok(rpcExecuteResult.getData(),rpcExecuteResult.getMessage());
            }
            return ExecuteResult.fail(rpcExecuteResult.getCode(),rpcExecuteResult.getMessage());
        }
        return ExecuteResult.ok(body);
   }
}
