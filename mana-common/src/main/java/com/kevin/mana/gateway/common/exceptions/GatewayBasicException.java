package com.kevin.mana.gateway.common.exceptions;

import com.kevin.mana.gateway.common.enums.GatewayResponseCode;

/**
 * 网关最基础的异常定义类
 *
 * @author kevin
 */
public class GatewayBasicException extends RuntimeException {

  protected GatewayResponseCode code;


  public GatewayBasicException(String message, GatewayResponseCode code) {
    super(message);
    this.code = code;
  }

  public GatewayBasicException(String message, Throwable cause, GatewayResponseCode code) {
    super(message, cause);
    this.code = code;
  }

  public GatewayBasicException(GatewayResponseCode code, Throwable cause) {
    super(cause);
    this.code = code;
  }

  public GatewayBasicException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace, GatewayResponseCode code) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }

  public GatewayResponseCode getCode() {
    return code;
  }


}
