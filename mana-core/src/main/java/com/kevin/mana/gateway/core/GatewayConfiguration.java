package com.kevin.mana.gateway.core;

import com.kevin.mana.gateway.common.consts.GatewayBasicConst;
import com.kevin.mana.gateway.common.utils.NetUtil;
import lombok.Data;

/**
 * 网关配置信息
 *
 * @author kevin
 */
@Data
public class GatewayConfiguration {

  /**
   * 默认端口
   */
  private Integer port = 8888;

  /**
   * 网关服务唯一ID
   */
  private String manaGatewayId = NetUtil.getLocalIp() + GatewayBasicConst.COLON_SEPARATOR + port;

  /**
   * 网关注册中心地址
   */
  private String registerAddress = "http://192.168.8.187:2379,http://192.168.8.187:2379,http://192.168.8.188:2379";

  /**
   * 网关命名空间
   */
  private String namespace = "mana-gateway-dev";

  /**
   * 网关服务器的CPU核数映射的线程数
   */
  private Integer processThread = Runtime.getRuntime().availableProcessors();

  /**
   * Netty的Boss线程数
   */
  private Integer eventLoopGroupBossNum = 1;

  /**
   * Netty的Work线程数
   */
  private Integer eventLoopGroupWorkNum = processThread;

  /**
   * 是否开启EPOLL
   */
  private Boolean useEPoll = true;

  /**
   * 是否开启Netty内存分配机制
   */
  private Boolean nettyAllocator = true;

  /**
   * http body报文最大大小
   */
  private Integer maxContentLength = 64 * 1024 * 1024;

  /**
   * dubbo开启连接数数量
   */
  private Integer dubboConnections = processThread;

  /**
   * 设置置响应模式, 默认是单异步模式. completableFuture回调处理结果： whenComplete(单异步)  or  whenCompleteAsync(双异步)
   */
  private Boolean whenComplete = true;

  /**
   * 网关队列配置：缓冲模式；
   */
  private String bufferType = ""; // RapidBufferHelper.FLUSHER;

  /**
   * 网关队列：内存队列大小
   */
  private Integer bufferSize = 1024 * 16;

  /**
   * 网关队列：阻塞/等待策略
   */
  private String waitStrategy = "blocking";

  /*=====Http Async参数选项=====*/

  /**
   * 连接超时时间
   */
  private Integer httpConnectTimeout = 30 * 1000;

  /**
   * 请求超时时间
   */
  private Integer httpRequestTimeout = 30 * 1000;

  /**
   * 客户端请求重试次数
   */
  private Integer httpMaxRequestRetry = 2;

  /**
   * 客户端请求最大连接数
   */
  private Integer httpMaxConnections = 10000;

  /**
   * 客户端每个地址支持的最大连接数
   */
  private Integer httpConnectionsPerHost = 8000;

  /**
   * 客户端空闲连接超时时间, 默认60秒
   */
  private Integer httpPooledConnectionIdleTimeout = 60 * 1000;


}
