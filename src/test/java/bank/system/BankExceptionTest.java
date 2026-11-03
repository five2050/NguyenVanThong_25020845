package bank.system;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Unit tests for bank-specific exception messages. */
class BankExceptionTest {
  @Test
  void formatsExceptionMessages() {
    assertTrue(new BankException("failure").getMessage().contains("failure"));
    assertTrue(new InsufficientFundsException(12.5).getMessage().contains("$12.50"));
    assertTrue(new InvalidFundingAmountException(-2.0).getMessage().contains("$-2.00"));
  }
}
