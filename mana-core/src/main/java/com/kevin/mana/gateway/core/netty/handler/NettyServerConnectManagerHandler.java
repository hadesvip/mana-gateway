package com.kevin.mana.gateway.core.netty.handler;

import com.kevin.mana.gateway.common.utils.RemotingUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接管理器
 *
 * @author kevin
 */
public class NettyServerConnectManagerHandler extends ChannelDuplexHandler {

  private static final Logger logger = LoggerFactory
      .getLogger(NettyServerConnectManagerHandler.class);

  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    String address = RemotingUtil.parseRemoteAddress(ctx.channel());
    logger.debug("NETTY SERVER PIPLINE: channelRegistered {}", address);
    super.channelRegistered(ctx);
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    String address = RemotingUtil.parseRemoteAddress(ctx.channel());
    logger.debug("NETTY SERVER PIPLINE: channelUnregistered {}", address);
    super.channelUnregistered(ctx);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    String address = RemotingUtil.parseRemoteAddress(ctx.channel());
    logger.debug("NETTY SERVER PIPLINE: channelActive {}", address);
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    String address = RemotingUtil.parseRemoteAddress(ctx.channel());
    logger.debug("NETTY SERVER PIPLINE: channelInactive {}", address);
    super.channelInactive(ctx);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent event = (IdleStateEvent) evt;
      if (event.state().equals(IdleState.ALL_IDLE)) {
        String address = RemotingUtil.parseRemoteAddress(ctx.channel());
        logger.warn("NETTY SERVER PIPLINE: userEventTriggered: IDLE {}", address);
        ctx.channel().close();
      }
    }
    ctx.fireUserEventTriggered(evt);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    String address = RemotingUtil.parseRemoteAddress(ctx.channel());
    logger.warn("NETTY SERVER PIPLINE: remoteAddr： {}, exceptionCaught {}", address, cause);
    ctx.channel().close();
  }
}
