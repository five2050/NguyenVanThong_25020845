package com.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculates shipping fees for standard and express deliveries.
 */
public class ShippingCalculator {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShippingCalculator.class);
  private static final String EXPRESS = "EXPRESS";
  private static final String STANDARD = "STANDARD";

  /**
   * Calculates a shipping fee.
   *
   * @param weight package weight, which must be positive
   * @param type shipping service type
   * @return shipping fee in the same currency units as the rate constants
   * @throws IllegalArgumentException if the weight or service type is invalid
   */
  public double calculate(double weight, String type) {
    if (weight <= 0) {
      IllegalArgumentException exception =
          new IllegalArgumentException("Weight must be positive");
      LOGGER.error("Shipping calculation rejected: weight={}, type={}", weight, type, exception);
      throw exception;
    }
    if (EXPRESS.equals(type)) {
      double fee = weight * 5000 + 20000;
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
