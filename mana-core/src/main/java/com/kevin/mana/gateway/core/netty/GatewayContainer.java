package com.kevin.mana.gateway.core.netty;

import static com.kevin.mana.gateway.common.enums.GatewayBufferEnum.GATEWAY_BUFFER_ENUM_MAP;

import com.kevin.mana.gateway.common.enums.GatewayBufferEnum;
import com.kevin.mana.gateway.core.GatewayConfiguration;
import com.kevin.mana.gateway.core.ext.LifeCycle;
import com.kevin.mana.gateway.core.ext.NettyProcessor;
import com.kevin.mana.gateway.core.netty.processor.NettyCoreProcessor;
import com.kevin.mana.gateway.core.netty.processor.NettyFlusherProcessor;
import com.kevin.mana.gateway.core.netty.processor.NettyMPMCProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网关容器
 *
 * @author kevin
 */
public class GatewayContainer implements LifeCycle {

  private static final Logger logger = LoggerFactory.getLogger(GatewayContainer.class);

  private final GatewayConfiguration configuration;

  private NettyHttpServer nettyHttpServer;

  private NettyProcessor nettyProcessor;

  public GatewayContainer(GatewayConfiguration configuration) {
    this.configuration = configuration;
    this.init();
  }

  @Override
  public void init() {
    logger.debug("初始化网关容器");
    //构建核心处理器
    NettyCoreProcessor nettyCoreProcessor = new NettyCoreProcessor();
    //缓冲模式
    String bufferType = configuration.getBufferType();
    if (GatewayBufferEnum.unSetBuffer(bufferType)) {
      nettyProcessor = nettyCoreProcessor;
    }
    //flusher
    if (GatewayBufferEnum.isFlusher(bufferType)) {
      nettyProcessor = new NettyFlusherProcessor(configuration, nettyCoreProcessor);
    }
    //mpmc
    if (GatewayBufferEnum.isMPMC(bufferType)) {
      nettyProcessor = new NettyMPMCProcessor(configuration, nettyCoreProcessor);
    }
    //创建NettyHttpServer
    nettyHttpServer = new NettyHttpServer(configuration, nettyCoreProcessor);
  }

  @Override
  public void start() {
    nettyProcessor.start();
    nettyHttpServer.start();
    logger.info("=======GatewayContainer start success========");
  }

  @Override
  public void shutdown() {
    nettyProcessor.shutdown();
    nettyHttpServer.shutdown();
    logger.info("=======GatewayContainer shutdown success========");
  }
}
