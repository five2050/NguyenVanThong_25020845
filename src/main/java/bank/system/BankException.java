package bank.system;

/**
 * Base checked exception for bank operations.
 */
public class BankException extends Exception {
  /**
   * Creates an exception with a descriptive message.
   *
   * @param message reason for the failure
   */
  public BankException(String message) {
    super(message);
  }
}
