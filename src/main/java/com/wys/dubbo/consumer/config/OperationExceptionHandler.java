package com.wys.dubbo.consumer.config;


import com.wys.dubbo.consumer.common.exception.BusinessException;
import com.wys.dubbo.consumer.common.exception.GlobalExceptionEnum;
import com.wys.dubbo.consumer.common.response.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * GlobalExceptionHandler
 *
 * @author chhy
 */
@RestControllerAdvice
@Slf4j
public class OperationExceptionHandler {


    //BusinessException异常
    @ExceptionHandler(BusinessException.class)
    public ExecuteResult<String> handle(BusinessException e) {
        return ExecuteResult.fail(e.getErrorCode(), e.getMessage());
   }

    /**
     * 添加@Validated 统一异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExecuteResult<String> validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                log.debug("field" + error.getField() + ", msg:" + error.getDefaultMessage());
                errorMsg.append(error.getDefaultMessage()).append("!");
           });

       }
        return ExecuteResult.fail(GlobalExceptionEnum.ERROR_FORMAT_PARAMETER.getCode(),errorMsg.toString());
   }

}
