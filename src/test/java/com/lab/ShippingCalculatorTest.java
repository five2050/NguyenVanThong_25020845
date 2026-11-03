package com.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Unit tests for the shipping fee calculator. */
class ShippingCalculatorTest {
  private final ShippingCalculator calculator = new ShippingCalculator();

  @Test
  void calculatesStandardShipping() {
    assertEquals(15000.0, calculator.calculate(5, "STANDARD"), 0.0);
  }

  @Test
  void calculatesExpressShipping() {
    assertEquals(45000.0, calculator.calculate(5, "EXPRESS"), 0.0);
  }

  @Test
  void rejectsInvalidWeight() {
    assertThrows(IllegalArgumentException.class, () -> calculator.calculate(-1, "STANDARD"));
    assertThrows(IllegalArgumentException.class, () -> calculator.calculate(0, "EXPRESS"));
  }

  @Test
  void rejectsUnknownOrMissingServiceType() {
    assertThrows(IllegalArgumentException.class, () -> calculator.calculate(1, "UNKNOWN"));
    assertThrows(IllegalArgumentException.class, () -> calculator.calculate(1, null));
  }
}
