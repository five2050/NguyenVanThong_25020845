package bank.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Unit tests for shared account behavior. */
class AccountTest {
  @Test
  void storesAndReplacesAccountState() {
    CheckingAccount account = new CheckingAccount(22L, 15.0);
    account.setAccountNumber(23L);
    account.setBalance(30.0);
    account.setTransactionList(null);

    assertEquals(23L, account.getAccountNumber());
    assertEquals(30.0, account.getBalance(), 0.0);
    assertTrue(account.getTransactionList().isEmpty());

    List<Transaction> transactions = new ArrayList<>();
    account.setTransactionList(transactions);
    assertTrue(account.getTransactionList() == transactions);
  }

  @Test
  void recordsOnlyNonNullTransactionsAndFormatsHistory() {
    CheckingAccount account = new CheckingAccount(5L, 25.0);
    account.addTransaction(null);
    account.addTransaction(new Transaction(
        Transaction.TYPE_DEPOSIT_CHECKING, 5.0, 25.0, 30.0));

    assertEquals(1, account.getTransactionList().size());
    assertTrue(account.getTransactionHistory().contains("tài khoản 5"));
    assertTrue(account.getTransactionHistory().contains("$30.00"));
  }

  @Test
  void comparesAccountsByAccountNumber() {
    final CheckingAccount first = new CheckingAccount(1L, 5.0);
    final CheckingAccount equal = new CheckingAccount(1L, 90.0);
    final SavingsAccount different = new SavingsAccount(2L, 5.0);

    assertTrue(first.equals(first));
    assertTrue(first.equals(equal));
    assertEquals(first.hashCode(), equal.hashCode());
    assertFalse(first.equals(different));
    assertFalse(first.equals(null));
    assertNotEquals(first, "account");
    assertTrue(new CheckingAccount(3L, 0.0).getTransactionHistory()
        .startsWith("Lịch sử giao dịch"));
  }
}
