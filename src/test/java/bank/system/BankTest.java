package bank.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Unit tests for bank directory and customer-file parsing. */
class BankTest {
  @TempDir Path temporaryDirectory;

  @Test
  void sortsCustomersWithoutMutatingTheStoredList() {
    Bank bank = new Bank();
    Customer zed = new Customer(200L, "Zed");
    Customer amy = new Customer(300L, "Amy");
    Customer anotherAmy = new Customer(100L, "Amy");
    bank.setCustomerList(Arrays.asList(zed, amy, anotherAmy));

    String byId = bank.getCustomersInfoByIdOrder();
    String byName = bank.getCustomersInfoByNameOrder();
    assertTrue(byId.indexOf("100") < byId.indexOf("200"));
    assertTrue(byName.indexOf("100") < byName.indexOf("300"));
    assertEquals("Zed", bank.getCustomerList().get(0).getFullName());
    bank.setCustomerList(null);
    assertEquals("", bank.getCustomersInfoByIdOrder());
    assertEquals("", bank.getCustomersInfoByNameOrder());
  }

  @Test
  void importsCustomerAndAccountsAndSkipsInvalidRecords() {
    String records = String.join("\n",
        "Lan Nguyen 123456789",
        "100 CHECKING 250.5",
        "200 SAVINGS 8000",
        "300 UNKNOWN 1",
        "not an account",
        "Minh Tran 987654321");
    Bank bank = new Bank();
    bank.readCustomerList(new ByteArrayInputStream(records.getBytes(StandardCharsets.UTF_8)));

    assertEquals(2, bank.getCustomerList().size());
    assertEquals(2, bank.getCustomerList().get(0).getAccountList().size());
    assertTrue(bank.getCustomerList().get(0).getAccountList().get(0) instanceof CheckingAccount);
    bank.readCustomerList(null);
  }

  @Test
  void loadsCustomerRecordsFromResolvedFilePath() throws IOException {
    Files.writeString(temporaryDirectory.resolve("customers.txt"), "An Nguyen 123456789");
    Bank bank = new Bank();

    bank.readCustomerFile(temporaryDirectory);

    assertEquals(123456789L, bank.getCustomerList().get(0).getIdNumber());
    assertThrows(IOException.class,
        () -> bank.readCustomerFile(temporaryDirectory.resolve("missing")));
  }
}
