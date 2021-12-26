package com.kevin.mana.gateway.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class OSUtil {

  private static final Logger logger = LoggerFactory.getLogger(OSUtil.class);

  private OSUtil() {
  }

  private static final SystemInfo SI = new SystemInfo();
  public static final String TWO_DECIMAL = "0.00";

  private static HardwareAbstractionLayer hal = SI.getHardware();

  public static double availablePhysicalMemorySize() {
    GlobalMemory memory = hal.getMemory();
    double availablePhysicalMemorySize =
        (memory.getAvailable() + memory.getSwapUsed()) / 1024.0 / 1024 / 1024;

    DecimalFormat df = new DecimalFormat(TWO_DECIMAL);
    df.setRoundingMode(RoundingMode.HALF_UP);
    return Double.parseDouble(df.format(availablePhysicalMemorySize));

  }

  public static double availablePhysicalMemorySizeProportion() {
    GlobalMemory memory = hal.getMemory();
    double availablePhysicalMemorySize =
        (memory.getAvailable() + memory.getSwapUsed()) / 1024.0 / 1024 / 1024;
    availablePhysicalMemorySize =
        availablePhysicalMemorySize / (memory.getTotal() / 1024.0 / 1024 / 1024);
    DecimalFormat df = new DecimalFormat(TWO_DECIMAL);
    df.setRoundingMode(RoundingMode.HALF_UP);
    return Double.parseDouble(df.format(1 - availablePhysicalMemorySize));
  }

  public static double loadAverage() {
    double loadAverage = hal.getProcessor().getSystemLoadAverage();

    DecimalFormat df = new DecimalFormat(TWO_DECIMAL);

    df.setRoundingMode(RoundingMode.HALF_UP);
    return Double.parseDouble(df.format(loadAverage));
  }

  public static Boolean checkResource(double cpuUsage, double reservedMemory) {
    double availablePhysicalMemorySize = OSUtil.availablePhysicalMemorySize();
    if (1 - OSUtil.cpuUsage() < cpuUsage || availablePhysicalMemorySize < reservedMemory) {
      logger.warn("网关已负载,cpu已使用[{}],内存已使用[{}]", OSUtil.cpuUsage(), availablePhysicalMemorySize);
      return false;
    } else {
      return true;
    }
  }

  public static double cpuUsage() {
    CentralProcessor processor = hal.getProcessor();
    double cpuUsage = processor.getSystemCpuLoad();
    DecimalFormat df = new DecimalFormat(TWO_DECIMAL);
    df.setRoundingMode(RoundingMode.HALF_UP);
    return Double.parseDouble(df.format(cpuUsage));
  }

}
