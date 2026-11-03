package bank.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A checking account.
 */
public class CheckingAccount extends Account {
  private static final Logger LOGGER = LoggerFactory.getLogger(CheckingAccount.class);

  /**
   * Creates a checking account.
   *
   * @param accountNumber account number
   * @param balance opening balance
   */
  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING, amount, initialBalance, getBalance()));
      LOGGER.info("Checking deposit completed: account={}, amount={}, balance={}",
          getAccountNumber(), amount, getBalance());
    } catch (BankException exception) {
      LOGGER.error("Checking deposit rejected: account={}, amount={}",
          getAccountNumber(), amount, exception);
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING, amount, initialBalance, getBalance()));
      LOGGER.info("Checking withdrawal completed: account={}, amount={}, balance={}",
          getAccountNumber(), amount, getBalance());
    } catch (BankException exception) {
      LOGGER.error("Checking withdrawal rejected: account={}, amount={}",
          getAccountNumber(), amount, exception);
    }
  }
}
