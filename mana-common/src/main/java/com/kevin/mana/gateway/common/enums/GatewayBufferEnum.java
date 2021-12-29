package com.kevin.mana.gateway.common.enums;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
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


  public static final Map<String, GatewayBufferEnum> GATEWAY_BUFFER_ENUM_MAP =
      ImmutableMap.<String, GatewayBufferEnum>builder()
          .put(FLUSHER.name(), FLUSHER)
          .put(MPMC.name(), MPMC)
          .build();


  public static boolean unSetBuffer(String bufferType) {
    return !GATEWAY_BUFFER_ENUM_MAP.containsKey(bufferType);
  }


  public static boolean isMPMC(String bufferType) {
    return MPMC.name().equals(bufferType);
  }


  public static boolean isFlusher(String bufferType) {
    return FLUSHER.name().equals(bufferType);
  }


}
