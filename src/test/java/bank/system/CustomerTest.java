package bank.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Unit tests for customer state and account membership. */
class CustomerTest {
  @Test
  void managesCustomerDetailsAndAccounts() {
    Customer customer = new Customer();
    customer.setIdNumber(12L);
    customer.setFullName("Lan Nguyen");
    assertEquals(12L, customer.getIdNumber());
    assertEquals("Lan Nguyen", customer.getFullName());
    assertTrue(customer.getCustomerInfo().contains("Lan Nguyen"));

    CheckingAccount account = new CheckingAccount(31L, 10.0);
    customer.addAccount(null);
    customer.addAccount(account);
    customer.addAccount(new CheckingAccount(31L, 99.0));
    assertEquals(1, customer.getAccountList().size());
    customer.removeAccount(new CheckingAccount(31L, 0.0));
    assertTrue(customer.getAccountList().isEmpty());

    customer.setAccountList(null);
    assertTrue(customer.getAccountList().isEmpty());
    List<Account> accounts = new ArrayList<>();
    customer.setAccountList(accounts);
    assertTrue(customer.getAccountList() == accounts);
  }
}
