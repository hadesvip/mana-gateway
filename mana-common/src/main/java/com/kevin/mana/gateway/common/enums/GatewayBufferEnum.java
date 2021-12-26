package com.kevin.mana.gateway.common.enums;

import lombok.Getter;

/**
 * 网关缓冲
 *
 * @author kevin
 */
@Getter
public enum GatewayBufferEnum {

  FLUSHER,
  MPMC;


  static boolean isMPMC(String bufferType) {
    return MPMC.name().equals(bufferType);
  }


  static boolean isFlusher(String bufferType) {
    return FLUSHER.name().equals(bufferType);
  }


}
