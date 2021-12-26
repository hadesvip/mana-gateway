package com.kevin.mana.gateway.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FastJsonConvertUtil {

  private static final Logger logger = LoggerFactory.getLogger(FastJsonConvertUtil.class);


  private FastJsonConvertUtil() {
  }


  private static final SerializerFeature[] featuresWithNullValue = {
      SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullBooleanAsFalse,
      SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero,
      SerializerFeature.WriteNullStringAsEmpty};

  /**
   * 将JSON字符串转换为实体对象
   */
  public static <T> T convertJSONToObject(String data, Class<T> clzss) {
    try {
      return JSON.parseObject(data, clzss);
    } catch (Exception e) {
      logger.error("将JSON字符串转换为实体对象,发生异常", e);
      return null;
    }
  }

  /**
   * 将JSONObject对象转换为实体对象
   */
  public static <T> T convertJSONToObject(JSONObject data, Class<T> clzss) {
    try {
      return JSONObject.toJavaObject(data, clzss);
    } catch (Exception e) {
      logger.error("将JSONObject对象转换为实体对象,发生异常", e);
      return null;
    }
  }

  /**
   * 将JSON字符串数组转为List集合对象
   */
  public static <T> List<T> convertJSONToArray(String data, Class<T> clzss) {
    try {
      return JSON.parseArray(data, clzss);
    } catch (Exception e) {
      logger.error("将JSON字符串数组转为List集合对象,发生异常", e);
      return null;
    }
  }

  /**
   * 将List<JSONObject>转为List集合对象
   */
  public static <T> List<T> convertJSONToArray(List<JSONObject> data, Class<T> clzss) {
    try {
      List<T> t = new ArrayList<T>();
      for (JSONObject jsonObject : data) {
        t.add(convertJSONToObject(jsonObject, clzss));
      }
      return t;
    } catch (Exception e) {
      logger.error("将List<JSONObject>转为List集合对象,发生异常", e);
      return null;
    }
  }

  /**
   * 将对象转为JSON字符串
   */
  public static String convertObjectToJSON(Object obj) {
    try {
      return JSON.toJSONString(obj);
    } catch (Exception e) {
      logger.error("将对象转为JSON字符串,发生异常", e);
      return null;
    }
  }

  /**
   * 将对象转为JSONObject对象
   */
  public static JSONObject convertObjectToJSONObject(Object obj) {
    try {
      return (JSONObject) JSONObject.toJSON(obj);
    } catch (Exception e) {
      logger.error("将对象转为JSONObject对象,发生异常", e);
      return null;
    }
  }


  /**
   * 将对象转为JSONObject对象
   */
  public static String convertObjectToJSONWithNullValue(Object obj) {
    try {
      return JSON.toJSONString(obj, featuresWithNullValue);
    } catch (Exception e) {
      logger.error("将对象转为JSONObject对象,发生异常", e);
      return null;
    }
  }

}
