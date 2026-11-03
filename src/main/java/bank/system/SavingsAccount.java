package bank.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A savings account with a withdrawal cap and minimum balance.
 */
public class SavingsAccount extends Account {
  private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccount.class);
  private static final double MAX_WITHDRAWAL = 1000.0;
  private static final double MINIMUM_BALANCE = 5000.0;

  /**
   * Creates a savings account.
   *
   * @param accountNumber account number
   * @param balance opening balance
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, getBalance()));
      LOGGER.info("Savings deposit completed: account={}, amount={}, balance={}",
          getAccountNumber(), amount, getBalance());
    } catch (BankException exception) {
      LOGGER.error("Savings deposit rejected: account={}, amount={}",
          getAccountNumber(), amount, exception);
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAWAL) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MINIMUM_BALANCE) {
        throw new InsufficientFundsException(amount);
      }
      doWithdrawing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, getBalance()));
      LOGGER.info("Savings withdrawal completed: account={}, amount={}, balance={}",
          getAccountNumber(), amount, getBalance());
    } catch (BankException exception) {
      LOGGER.error("Savings withdrawal rejected: account={}, amount={}",
          getAccountNumber(), amount, exception);
    }
  }
}
