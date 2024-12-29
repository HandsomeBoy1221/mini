package com.ceshiren.mini.util;


public enum ResultCode {
    SUCCESS(0, "成功"),//200
    FAIL(1, "失败"),//400
    UNAUTHORIZED(401, "未认证或认证失败"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    PARAMETER_ERROR(10001, "参数不能为空"),
    PARAMETER_NOT_EXIST(10002, "参数不存在"),
    UNREGISTER(40013,"用户未注册"),
    PWDWRONG(40014,"用户密码错误"),
    DELETEERROR(90001,"删除失败"),
    UPDATERROR(90009,"更新失败"),
    INTERNAL_SERVER_ERROR(5001, "系统繁忙，请稍后重试");
    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
