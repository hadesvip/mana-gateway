package com.kevin.mana.gateway.core.netty.processor;

import com.kevin.mana.gateway.core.context.HttpRequestWrapper;
import com.kevin.mana.gateway.core.ext.NettyProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心逻辑执行器
 *
 * @author kevin
 */
public class NettyCoreProcessor implements NettyProcessor {


  private static final Logger logger = LoggerFactory.getLogger(NettyCoreProcessor.class);


  @Override
  public void process(HttpRequestWrapper httpRequestWrapper) {
    ChannelHandlerContext ctx = httpRequestWrapper.getCtx();
    FullHttpRequest fullHttpRequest = httpRequestWrapper.getFullHttpRequest();




  }

  @Override
  public void start() {

  }

  @Override
  public void shutdown() {

  }
}
