package com.qingshan.mall.model;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

  private int code;
  private String msg;

  public ServiceException(ErrorCodeEnum errorCodeEnum) {
    this.code = errorCodeEnum.getCode();
    this.msg = errorCodeEnum.getMsg();
  }

  public ServiceException(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ServiceException exception(ErrorCodeEnum errorCodeEnum) {
    return new ServiceException(errorCodeEnum);
  }

  public static ServiceException exception(int code, String msg) {
    return new ServiceException(code, msg);
  }

  public static ServiceException exception(String msg) {
    return new ServiceException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), msg);
  }
}
