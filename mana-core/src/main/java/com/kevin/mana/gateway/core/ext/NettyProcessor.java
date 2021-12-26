package com.kevin.mana.gateway.core.ext;

import com.kevin.mana.gateway.core.context.HttpRequestWrapper;

/**
 * netty核心逻辑处理器
 *
 * @author kevin
 */
public interface NettyProcessor {

  /**
   * 核心执行方法
   */
  void process(HttpRequestWrapper httpRequestWrapper);

  /**
   * 执行器启动方法
   */
  void start();

  /**
   * 执行器资源释放/关闭方法
   */
  void shutdown();


}
