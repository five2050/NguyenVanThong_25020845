package bank.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base type for bank accounts.
 */
public abstract class Account {
  /** Transaction type for a checking account deposit. */
  public static final String CHECKING_TYPE = "CHECKING";
  /** Transaction type for a savings account deposit. */
  public static final String SAVINGS_TYPE = "SAVINGS";

  private long accountNumber;
  private double balance;
  private List<Transaction> transactions = new ArrayList<>();

  /**
   * Creates an account with the supplied number and opening balance.
   *
   * @param accountNumber unique account number
   * @param balance opening balance
   */
  protected Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
  }

  /**
   * Returns this account number.
   *
   * @return account number
   */
  public long getAccountNumber() {
    return accountNumber;
  }

  /**
   * Updates this account number.
   *
   * @param accountNumber new account number
   */
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * Returns the current balance.
   *
   * @return current balance
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Updates the account balance.
   *
   * @param balance new balance
   */
  protected void setBalance(double balance) {
    this.balance = balance;
  }

  /**
   * Returns this account's transaction history.
   *
   * @return transactions in insertion order
   */
  public List<Transaction> getTransactionList() {
    return transactions;
  }

  /**
   * Replaces this account's transaction history.
   *
   * @param transactionList transactions to store, or {@code null} for an empty list
   */
  public void setTransactionList(List<Transaction> transactionList) {
    transactions = transactionList == null ? new ArrayList<>() : transactionList;
  }

  /**
   * Deposits an amount according to this account's rules.
   *
   * @param amount amount to deposit
   */
  public abstract void deposit(double amount);

  /**
   * Withdraws an amount according to this account's rules.
   *
   * @param amount amount to withdraw
   */
  public abstract void withdraw(double amount);

  /**
   * Validates and applies a deposit.
   *
   * @param amount positive deposit amount
   * @throws InvalidFundingAmountException if the amount is not positive
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  /**
   * Validates and applies a withdrawal.
   *
   * @param amount positive withdrawal amount
   * @throws InvalidFundingAmountException if the amount is not positive
   * @throws InsufficientFundsException if the balance is too low
   */
  protected void doWithdrawing(double amount)
      throws InvalidFundingAmountException, InsufficientFundsException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Adds a non-null transaction.
   *
   * @param transaction transaction to add
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactions.add(transaction);
    }
  }

  /**
   * Formats the full transaction history.
   *
   * @return formatted transaction history
   */
  public String getTransactionHistory() {
    StringBuilder history =
        new StringBuilder("Lịch sử giao dịch của tài khoản ").append(accountNumber).append(":\n");
    for (int index = 0; index < transactions.size(); index++) {
      if (index > 0) {
        history.append('\n');
      }
      history.append(transactions.get(index).getTransactionSummary());
    }
    return history.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Account)) {
      return false;
    }
    Account account = (Account) object;
    return accountNumber == account.accountNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNumber);
  }
}
