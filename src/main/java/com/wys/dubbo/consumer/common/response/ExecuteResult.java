package com.wys.dubbo.consumer.common.response;

import com.wys.dubbo.consumer.common.exception.GlobalExceptionEnum;
import org.springframework.validation.ObjectError;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ExecuteResult<T> implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -4398792451408794749L;

    /**
     * success
     */
    private boolean success;

    /**
     * T <>
     */
    private T data;

    /**
     * message
     */
    private String message;

    /**
     * code 错误码
     */
    private String code;

    /**
     * url
     * @return
     */
    private String url;

    private GlobalExceptionEnum exceptionEnum;

    public void setExecuteResultEnum(GlobalExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
   }
    public boolean isSuccess() {
        return success;
  }
    public void setSuccess(boolean success) {
        this.success = success;
  }

    public T getData() {
        return data;
  }
    public void setData(T data) {
        this.data = data;
  }

    public String getMessage() {
        return exceptionEnum==null?message:exceptionEnum.getMessage();
  }
    public void setMessage(String message) {
        this.message = message;
  }

    public String getCode() {
        return exceptionEnum==null?code:exceptionEnum.getCode();
  }
    public void setCode(String code) {
        this.code = code;
  }

    public String getUrl() {
        return url;
  }
    public void setUrl(String url) {
        this.url = url;
  }

    /**
     *
     * @Title: ok
     * @param @param data
     * @param @return
     * @return ExecuteResult<T>
     * @throws
     */
    public static <T> ExecuteResult<T> ok(T data) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        result.setData(data);
        result.setSuccess(true);
        result.setExecuteResultEnum(GlobalExceptionEnum.SUCCESS);
        return result;
  }

    /**
     *
     * @param message
     * @return
     */
    public static <T> ExecuteResult<T> ok(String message) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        result.setData(null);
        result.setSuccess(true);
        result.setCode(GlobalExceptionEnum.SUCCESS.getCode());
        result.setMessage(message);
        return result;
  }

    /**
     *
     * @Title: ok
     * @param @param data
     * @param @param message
     * @param @return
     * @return ExecuteResult<T>
     * @throws
     */
    public static <T> ExecuteResult<T> ok(T data, String message) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        result.setData(data);
        result.setSuccess(true);
        result.setCode(GlobalExceptionEnum.SUCCESS.getCode());
        result.setMessage(message);
        return result;
  }

    /**
     *
     * @Title: ok
     * @param @return
     * @return ExecuteResult<T>
     * @throws
     */
    public static <T> ExecuteResult<T> ok() {
        return ok(null);
  }


    public static <T> ExecuteResult<T> fail(String message) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        result.setMessage(message);
        result.setCode(GlobalExceptionEnum.RUNTIME_EXCEPTION.getCode());
        result.setSuccess(false);
        return result;
   }
    /**
     *
     * @Title: failure
     * @param @param message
     * @param @param data
     * @param @return
     * @return ExecuteResult<T>
     * @throws
     */
    public static <T> ExecuteResult<T> fail(String errorCode, String message) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        result.setMessage(message);
        result.setCode(errorCode);
        result.setSuccess(false);
        return result;
  }

    /**
     *
     * @Title: failure
     * @param @param errorCode
     * @param @param message
     * @param @param data
     * @param @return
     * @return ExecuteResult<T>
     * @throws
     */
    public static <T> ExecuteResult<T> fail(String errorCode, String message, T data) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        result.setCode(errorCode);
        result.setMessage(message);
        result.setData(data);
        result.setSuccess(false);
        return result;
  }

    /**
     *
     * @Title: failure
     * @param @param data
     * @param @return
     * @return HttpJsonResult<T>
     * @throws
     */
    public static <T> ExecuteResult<T> paramError(List<ObjectError> errors) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        String errorMessage = errors.stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(";"));
        result.setMessage(errorMessage);
        return result;
  }


    public static <T> ExecuteResult<T> fail(GlobalExceptionEnum exceptionEnum,T data) {
        ExecuteResult<T> result = new ExecuteResult<T>();
        result.setExecuteResultEnum(exceptionEnum);
        result.setSuccess(false);
        result.setData(data);
        return result;
   }

    public static <T> ExecuteResult<T> fail(GlobalExceptionEnum exceptionEnum) {
        return fail(exceptionEnum,null);
   }

}

