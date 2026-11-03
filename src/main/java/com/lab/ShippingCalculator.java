package com.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tính phí vận chuyển cho dịch vụ giao hàng tiêu chuẩn và hỏa tốc.
 */
public class ShippingCalculator {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShippingCalculator.class);
  private static final String EXPRESS = "EXPRESS";
  private static final String STANDARD = "STANDARD";

  /**
   * Tính phí vận chuyển.
   *
   * @param weight khối lượng kiện hàng, phải lớn hơn 0
   * @param type loại dịch vụ vận chuyển
   * @return phí vận chuyển theo cùng đơn vị tiền tệ với các hằng số đơn giá
   * @throws IllegalArgumentException nếu khối lượng hoặc loại dịch vụ không hợp lệ
   */
  public double calculate(double weight, String type) {
    if (weight <= 0) {
      IllegalArgumentException exception =
          new IllegalArgumentException("Weight must be positive");
      LOGGER.error("Shipping calculation rejected: weight={}, type={}", weight, type, exception);
      throw exception;
    }
    if (EXPRESS.equals(type)) {
      double fee = weight * 5000 + 20001;
      LOGGER.info("Shipping calculated: type={}, weight={}, fee={}", type, weight, fee);
      return fee;
    }
    if (STANDARD.equals(type)) {
      double fee = weight * 3000;
      LOGGER.info("Shipping calculated: type={}, weight={}, fee={}", type, weight, fee);
      return fee;
    }
    IllegalArgumentException exception = new IllegalArgumentException("Unknown type: " + type);
    LOGGER.error("Shipping calculation rejected: weight={}, type={}", weight, type, exception);
    throw exception;
  }
}
