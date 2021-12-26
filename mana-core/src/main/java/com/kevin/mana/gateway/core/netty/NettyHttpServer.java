package com.kevin.mana.gateway.core.netty;

import com.kevin.mana.gateway.common.utils.RemotingUtil;
import com.kevin.mana.gateway.core.GatewayConfiguration;
import com.kevin.mana.gateway.core.ext.LifeCycle;
import com.kevin.mana.gateway.core.ext.NettyProcessor;
import com.kevin.mana.gateway.core.netty.handler.NettyHttpServerHandler;
import com.kevin.mana.gateway.core.netty.handler.NettyServerConnectManagerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.net.InetSocketAddress;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kevin
 */
public class NettyHttpServer implements LifeCycle {

  private static final Logger logger = LoggerFactory.getLogger(NettyHttpServer.class);

  private Integer port = 8888;

  private final GatewayConfiguration configuration;

  private ServerBootstrap serverBootstrap;

  private EventLoopGroup eventLoopGroupBoss;

  private EventLoopGroup eventLoopGroupWork;

  private NettyProcessor nettyProcessor;

  public boolean useEpoll() {
    return configuration.getUseEpoll() && RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
  }

  public NettyHttpServer(GatewayConfiguration configuration) {
    this.configuration = configuration;
    if (configuration.getPort() > 0 && configuration.getPort() < 65535) {
      this.port = configuration.getPort();
    }
    //初始化nettyHttpServer
    this.init();
  }

  @Override
  public void init() {
    serverBootstrap = new ServerBootstrap();
    if (useEpoll()) {
      this.eventLoopGroupBoss = new EpollEventLoopGroup(configuration.getEventLoopGroupBossNum(),
          new DefaultThreadFactory("NettyBossEpoll"));
      this.eventLoopGroupWork = new EpollEventLoopGroup(configuration.getEventLoopGroupWorkNum(),
          new DefaultThreadFactory("NettyWorkEpoll"));
    } else {
      this.eventLoopGroupBoss = new NioEventLoopGroup(configuration.getEventLoopGroupBossNum(),
          new DefaultThreadFactory("NettyBossNio"));
      this.eventLoopGroupWork = new NioEventLoopGroup(configuration.getEventLoopGroupWorkNum(),
          new DefaultThreadFactory("NettyWorkNio"));
    }
  }

  @Override
  public void start() {
    ServerBootstrap handler = this.serverBootstrap
        .group(eventLoopGroupBoss, eventLoopGroupWork)
        .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
        //sync + accept = backlog
        .option(ChannelOption.SO_BACKLOG, 1024)
        //tcp端口重绑定
        .option(ChannelOption.SO_REUSEADDR, true)
        //如果在两小时内没有数据通信的时候，TCP会自动发送一个活动探测数据报文
        .option(ChannelOption.SO_KEEPALIVE, false)
        //该参数的左右就是禁用Nagle算法，使用小数据传输时合并
        .option(ChannelOption.TCP_NODELAY, true)
        //设置发送数据缓冲区大小
        .option(ChannelOption.SO_SNDBUF, 65535)
        //设置接收数据缓冲区大小
        .option(ChannelOption.SO_RCVBUF, 65535)
        .localAddress(new InetSocketAddress(this.port))
        .childHandler(new ChannelInitializer<Channel>() {
          @Override
          protected void initChannel(Channel channel) throws Exception {
            channel.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(configuration.getMaxContentLength()),
                new HttpServerExpectContinueHandler(),
                new NettyServerConnectManagerHandler(),
                new NettyHttpServerHandler(nettyProcessor)
            );
          }
        });
    if (configuration.getNettyAllocator()) {
      handler.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    }
    try {
      this.serverBootstrap.bind().sync();
      logger.info("============= mana gateway Server StartUp On Port: {}================",
          this.port);
    } catch (Exception e) {
      throw new RuntimeException("this.serverBootstrap.bind().sync() fail!", e);
    }

  }

  @Override
  public void shutdown() {
    if (Objects.nonNull(eventLoopGroupBoss)) {
      eventLoopGroupBoss.shutdownGracefully();
    }
    if (Objects.nonNull(eventLoopGroupWork)) {
      eventLoopGroupWork.shutdownGracefully();
    }
  }


}
