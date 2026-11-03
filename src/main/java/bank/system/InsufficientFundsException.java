package bank.system;

import java.util.Locale;

/**
 * Indicates that an account cannot cover a requested withdrawal.
 */
public class InsufficientFundsException extends BankException {
  /**
   * Creates the exception for a withdrawal amount.
   *
   * @param amount requested withdrawal
   */
  public InsufficientFundsException(double amount) {
    super("Số dư tài khoản không đủ $" + String.format(Locale.US, "%.2f", amount)
        + " để thực hiện giao dịch");
  }
}
