package com.kevin.mana.gateway.core.netty.processor;

import com.kevin.mana.gateway.core.GatewayConfiguration;
import com.kevin.mana.gateway.core.context.HttpRequestWrapper;
import com.kevin.mana.gateway.core.ext.NettyProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MPMC核心实现
 *
 * @author kevin
 */
public class NettyMPMCProcessor implements NettyProcessor {

  private static final Logger logger = LoggerFactory.getLogger(NettyMPMCProcessor.class);

  private final GatewayConfiguration configuration;

  private final NettyCoreProcessor nettyCoreProcessor;

  public NettyMPMCProcessor(GatewayConfiguration configuration,
      NettyCoreProcessor nettyCoreProcessor) {
    this.configuration = configuration;
    this.nettyCoreProcessor = nettyCoreProcessor;
  }


  @Override
  public void process(HttpRequestWrapper httpRequestWrapper) {

  }

  @Override
  public void start() {

  }

  @Override
  public void shutdown() {

  }
}
