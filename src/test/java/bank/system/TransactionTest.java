package bank.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Unit tests for transaction formatting and accessors. */
class TransactionTest {
  @Test
  void updatesTransactionFieldsAndFormatsAllTypes() {
    Transaction transaction = new Transaction(0, 1.0, 2.0, 3.0);
    transaction.setType(Transaction.TYPE_DEPOSIT_CHECKING);
    transaction.setAmount(10.0);
    transaction.setInitialBalance(20.0);
    transaction.setFinalBalance(30.0);
    assertEquals(Transaction.TYPE_DEPOSIT_CHECKING, transaction.getType());
    assertEquals(10.0, transaction.getAmount(), 0.0);
    assertEquals(20.0, transaction.getInitialBalance(), 0.0);
    assertEquals(30.0, transaction.getFinalBalance(), 0.0);
    assertEquals("Nạp tiền vãng lai", Transaction.getTypeString(transaction.getType()));
    assertEquals("Rút tiền vãng lai",
        Transaction.getTypeString(Transaction.TYPE_WITHDRAW_CHECKING));
    assertEquals("Nạp tiền tiết kiệm",
        Transaction.getTypeString(Transaction.TYPE_DEPOSIT_SAVINGS));
    assertEquals("Rút tiền tiết kiệm",
        Transaction.getTypeString(Transaction.TYPE_WITHDRAW_SAVINGS));
    assertEquals("Không rõ", Transaction.getTypeString(-1));
    assertTrue(transaction.getTransactionSummary().contains("$30.00"));
  }
}
