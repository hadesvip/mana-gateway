package com.kevin.mana.gateway.core;


import com.kevin.mana.gateway.common.utils.PropertiesUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网关配置信息加载类
 * <p>
 * 优先级规则：运行参数>jvm参数>环境变量>配置文件>configuration默认属性值
 *
 * @author kevin
 */
public class GatewayConfigurationLoader {

  private static final Logger logger = LoggerFactory.getLogger(GatewayConfigurationLoader.class);

  private static final GatewayConfigurationLoader INSTANCE = new GatewayConfigurationLoader();

  /**
   * 网关配置
   */
  private final GatewayConfiguration configuration = new GatewayConfiguration();

  private final static String CONFIG_ENV_PREFIEX = "MANA_GATEWAY_ID_";

  private final static String CONFIG_JVM_PREFIEX = "mana_gateway_id.";

  private final static String CONFIG_FILE = "mana_gateway.properties";


  private GatewayConfigurationLoader() {
  }

  public static GatewayConfigurationLoader getInstance() {
    return INSTANCE;
  }

  public static GatewayConfiguration getGatewayConfiguration() {
    return INSTANCE.configuration;
  }

  /**
   * 加载配置文件
   */
  public GatewayConfiguration load(String[] args) {
    //from config file
    {
      try (InputStream is = GatewayConfigurationLoader.class.getClassLoader()
          .getResourceAsStream(CONFIG_FILE)) {
        if (Objects.nonNull(is)) {
          Properties prop = new Properties();
          prop.load(is);
          PropertiesUtil.propToObject(prop, configuration);
        }
      } catch (IOException e) {
        logger.warn("#GatewayConfigurationLoader# load config file: {} is error", CONFIG_FILE, e);
      }
    }
    //from environment
    {
      Map<String, String> env = System.getenv();
      Properties prop = new Properties();
      prop.putAll(env);
      PropertiesUtil.propToObject(prop, configuration, CONFIG_ENV_PREFIEX);
    }
    //from jvm
    {
      Properties prop = System.getProperties();
      PropertiesUtil.propToObject(prop, configuration, CONFIG_JVM_PREFIEX);
    }

    //from runner commandline args
    {
      if (ArrayUtils.isNotEmpty(args)) {
        Properties prop = new Properties();
        for (String arg : args) {
          if (arg.startsWith("--") && arg.contains("=")) {
            prop.put(arg.substring(2, arg.indexOf("=")), arg.substring(arg.indexOf("=") + 1));
          }
        }
        PropertiesUtil.propToObject(prop, configuration);
      }
    }

    return configuration;
  }


}
