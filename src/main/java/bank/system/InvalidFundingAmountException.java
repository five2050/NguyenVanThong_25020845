package bank.system;

import java.util.Locale;

/**
 * Indicates that a deposit or withdrawal amount is invalid.
 */
public class InvalidFundingAmountException extends BankException {
  /**
   * Creates the exception for the invalid amount.
   *
   * @param amount invalid amount
   */
  public InvalidFundingAmountException(double amount) {
    super("Số tiền không hợp lệ: $" + String.format(Locale.US, "%.2f", amount));
  }
}
