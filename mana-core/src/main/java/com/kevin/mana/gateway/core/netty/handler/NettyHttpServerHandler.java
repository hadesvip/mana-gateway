package com.kevin.mana.gateway.core.netty.handler;

import com.kevin.mana.gateway.core.context.HttpRequestWrapper;
import com.kevin.mana.gateway.core.ext.NettyProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心处理handler
 *
 * @author kevin
 */
public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(NettyHttpServerHandler.class);

  private final NettyProcessor nettyProcessor;

  public NettyHttpServerHandler(NettyProcessor nettyProcessor) {
    this.nettyProcessor = nettyProcessor;
  }


  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    if (msg instanceof HttpRequest) {
      FullHttpRequest request = (FullHttpRequest) msg;
      HttpRequestWrapper httpRequestWrapper = new HttpRequestWrapper();
      httpRequestWrapper.setFullHttpRequest(request);
      httpRequestWrapper.setCtx(ctx);
      //执行核心逻辑
      nettyProcessor.process(httpRequestWrapper);
    } else {
      logger.error("#NettyHttpServerHandler.channelRead# message type is not httpRequest: {}", msg);
      boolean release = ReferenceCountUtil.release(msg);
      if (!release) {
        logger.error("#NettyHttpServerHandler.channelRead# release fail 资源释放失败");
      }
    }
  }
}
