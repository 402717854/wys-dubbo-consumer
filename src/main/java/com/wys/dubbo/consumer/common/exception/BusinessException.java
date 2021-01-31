package com.wys.dubbo.consumer.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * @Author: 86133
 * @Date: 2021/01/27
 * @Description:: 业务异常
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class BusinessException extends RuntimeException{
    private String errorCode;

    public BusinessException(String message) {
        super(message);
   }

    /**
     * @param errorCode
     * @param message
     */
    public BusinessException(String errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
   }

    public BusinessException(GlobalExceptionEnum globalExceptionEnum) {
        super(globalExceptionEnum.getMessage());
        this.setErrorCode(globalExceptionEnum.getCode());
   }

    public BusinessException(GlobalExceptionEnum globalExceptionEnum , Object... obj) {
        super(MessageFormat.format(globalExceptionEnum.getMessage(),obj));
        this.setErrorCode(globalExceptionEnum.getCode());
   }
}
