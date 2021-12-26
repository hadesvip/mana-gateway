package com.kevin.mana.gateway.core.ext;

/**
 * 生命周期
 *
 * @author wangyong
 */
public interface LifeCycle {


  /**
   * 初始化操作
   */
  void init();

  /**
   * 启动
   */
  void start();


  /**
   * 关闭
   */
  void shutdown();


}
