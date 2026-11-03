package bank.system;

import java.util.Locale;

/**
 * A completed account transaction.
 */
public class Transaction {
  /** Checking deposit type. */
  public static final int TYPE_DEPOSIT_CHECKING = 1;
  /** Checking withdrawal type. */
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  /** Savings deposit type. */
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  /** Savings withdrawal type. */
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Creates a transaction record.
   *
   * @param type transaction type
   * @param amount transaction amount
   * @param initialBalance balance before the transaction
   * @param finalBalance balance after the transaction
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  /**
   * Returns the transaction type.
   *
   * @return transaction type
   */
  public int getType() {
    return type;
  }

  /**
   * Updates the transaction type.
   *
   * @param type new transaction type
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Returns the amount.
   *
   * @return transaction amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Updates the amount.
   *
   * @param amount new amount
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * Returns the initial balance.
   *
   * @return initial balance
   */
  public double getInitialBalance() {
    return initialBalance;
  }

  /**
   * Updates the initial balance.
   *
   * @param initialBalance new initial balance
   */
  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  /**
   * Returns the final balance.
   *
   * @return final balance
   */
  public double getFinalBalance() {
    return finalBalance;
  }

  /**
   * Updates the final balance.
   *
   * @param finalBalance new final balance
   */
  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Returns a localized label for a transaction type.
   *
   * @param transactionType type to describe
   * @return localized type label
   */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Formats this transaction for display.
   *
   * @return formatted transaction summary
   */
  public String getTransactionSummary() {
    return String.format(
        Locale.US,
        "- Kiểu giao dịch: %s. Số dư ban đầu: $%.2f. Số tiền: $%.2f. Số dư cuối: $%.2f.",
        getTypeString(type),
        initialBalance,
        amount,
        finalBalance);
  }
}
