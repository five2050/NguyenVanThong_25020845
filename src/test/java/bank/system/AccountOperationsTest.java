package bank.system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit tests for checking and savings account transaction rules. */
class AccountOperationsTest {
  @Test
  void checkingAccountRecordsSuccessfulOperationsAndRejectsInvalidAmounts() {
    CheckingAccount account = new CheckingAccount(1L, 100.0);
    account.deposit(20.0);
    account.withdraw(30.0);
    account.withdraw(1000.0);
    account.deposit(0.0);

    assertEquals(90.0, account.getBalance(), 0.0);
    assertEquals(2, account.getTransactionList().size());
    assertEquals(Transaction.TYPE_DEPOSIT_CHECKING, account.getTransactionList().get(0).getType());
    assertEquals(Transaction.TYPE_WITHDRAW_CHECKING, account.getTransactionList().get(1).getType());
  }

  @Test
  void savingsAccountEnforcesWithdrawalRules() {
    SavingsAccount account = new SavingsAccount(2L, 7000.0);
    account.deposit(500.0);
    account.withdraw(1000.0);
    account.withdraw(1001.0);
    account.withdraw(1000.0);
    account.deposit(-1.0);

    assertEquals(5500.0, account.getBalance(), 0.0);
    assertEquals(3, account.getTransactionList().size());
    assertEquals(Transaction.TYPE_DEPOSIT_SAVINGS, account.getTransactionList().get(0).getType());
    assertEquals(Transaction.TYPE_WITHDRAW_SAVINGS, account.getTransactionList().get(1).getType());
    assertEquals(Transaction.TYPE_WITHDRAW_SAVINGS, account.getTransactionList().get(2).getType());
  }
}
