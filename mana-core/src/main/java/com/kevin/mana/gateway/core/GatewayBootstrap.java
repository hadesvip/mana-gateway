package com.kevin.mana.gateway.core;

/**
 * 网关项目启动入口
 *
 * @author kevin
 */
public class GatewayBootstrap {

  public static void main(String[] args) {

    //1.加载网关配置
    GatewayConfigurationLoader.getInstance().load(args);

    //2.插件初始化工作


    //3.初始化服务注册管理中心，监听动态配置的新增、修改、删除

    //4.启动容器

  }


}
