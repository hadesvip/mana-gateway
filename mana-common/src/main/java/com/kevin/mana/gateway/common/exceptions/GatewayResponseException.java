package com.kevin.mana.gateway.common.exceptions;

import com.kevin.mana.gateway.common.enums.GatewayResponseCode;

public class GatewayResponseException extends GatewayBasicException {

  public GatewayResponseException() {
    this(GatewayResponseCode.INTERNAL_ERROR);
  }

  public GatewayResponseException(GatewayResponseCode code) {
    super(code.getMessage(), code);
  }

  public GatewayResponseException(Throwable cause, GatewayResponseCode code) {
    super(code.getMessage(), cause, code);
    this.code = code;
  }
}