package com.ceshiren.mini.exception;

//自定义的服务异常
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 4564124491192825748L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
