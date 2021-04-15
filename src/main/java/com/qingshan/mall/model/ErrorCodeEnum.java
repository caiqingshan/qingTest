package com.qingshan.mall.model;

/**
 * 响应码枚举类
 */
public enum ErrorCodeEnum {
    SUCCESS(0, "SUCCESS"),
    NOT_LOGIN(1, "未登录"),
    PARAM_ERROR(2, "参数错误"),
    NOT_REGISTER(3, "未注册"),
    INFO_ERROR(4,"信息错误"),
    SYS_UPGRADE(5,"系统升级中"),
    NO_ACCESS(6,"无权访问"),
    SYSTEM_ERROR(999, "系统异常");


    private int code;
    private String msg;

    // 构造方法
    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 普通方法
    public static String getMsg(int code) {
        for (ErrorCodeEnum c : ErrorCodeEnum.values()) {
            if (code == c.getCode()) {
                return c.msg;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
