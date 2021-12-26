package com.kevin.mana.gateway.core.context;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

/**
 * http请求包装类
 *
 * @author kevin
 */
@Data
public class HttpRequestWrapper {

  private FullHttpRequest fullHttpRequest;

  private ChannelHandlerContext ctx;


  @Override
  public String toString() {
    return "{"
        + "\"fullHttpRequest\":"
        + fullHttpRequest
        + ",\"ctx\":"
        + ctx
        + '}';
  }
}
