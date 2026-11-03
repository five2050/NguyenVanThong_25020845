package bank.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bank customer directory and executable application entry point.
 */
public class Bank {
  private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);
  private static final String CUSTOMER_ID_PATTERN = "\\d{9}";
  private List<Customer> customerList = new ArrayList<>();

  /** Creates an empty bank directory. */
  public Bank() {
    // The field initializer establishes an empty customer list.
  }

  /**
   * Returns all registered customers.
   *
   * @return customers in insertion order
   */
  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Replaces the customer list.
   *
   * @param customers new customers, or {@code null} for an empty list
   */
  public void setCustomerList(List<Customer> customers) {
    customerList = customers == null ? new ArrayList<>() : customers;
  }

  /**
   * Reads customer records and their accounts from a text stream. A customer
   * line ends in a nine-digit identifier; account lines use number, type, balance.
   *
   * @param inputStream source to read; {@code null} is ignored
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      LOGGER.warn("Customer import skipped: input stream is null");
      return;
    }
    LOGGER.info("Customer import started");
    Customer currentCustomer = null;
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
          continue;
        }
        try {
          currentCustomer = readRecord(trimmedLine, currentCustomer);
        } catch (IllegalArgumentException exception) {
          LOGGER.error("Skipping invalid customer record: {}", trimmedLine, exception);
        }
      }
      LOGGER.info("Customer import completed: customerCount={}", customerList.size());
    } catch (IOException exception) {
      LOGGER.error("Customer import failed", exception);
    }
  }

  /**
   * Imports customers from a file located under the supplied directory.
   *
   * @param dataDirectory directory containing the customer data file
   * @throws IOException if the file cannot be opened
   */
  public void readCustomerFile(Path dataDirectory) throws IOException {
    Path customerFile = dataDirectory.resolve("customers.txt");
    try (InputStream inputStream = Files.newInputStream(customerFile)) {
      readCustomerList(inputStream);
    }
  }

  private Customer readRecord(String line, Customer currentCustomer) {
    int lastSpace = line.lastIndexOf(' ');
    if (lastSpace > 0 && line.substring(lastSpace + 1).matches(CUSTOMER_ID_PATTERN)) {
      String name = line.substring(0, lastSpace).trim();
      Customer customer = new Customer(Long.parseLong(line.substring(lastSpace + 1)), name);
      customerList.add(customer);
      LOGGER.info("Customer added: id={}, name={}", customer.getIdNumber(), customer.getFullName());
      return customer;
    }
    if (currentCustomer == null) {
      throw new IllegalArgumentException("Account record appears before a customer record");
    }
    String[] parts = line.split("\\s+");
    if (parts.length < 3) {
      throw new IllegalArgumentException("Account record must contain number, type, and balance");
    }
    long accountNumber = Long.parseLong(parts[0]);
    double balance = Double.parseDouble(parts[2]);
    if (Account.CHECKING_TYPE.equals(parts[1])) {
      currentCustomer.addAccount(new CheckingAccount(accountNumber, balance));
    } else if (Account.SAVINGS_TYPE.equals(parts[1])) {
      currentCustomer.addAccount(new SavingsAccount(accountNumber, balance));
    } else {
      throw new IllegalArgumentException("Unknown account type: " + parts[1]);
    }
    return currentCustomer;
  }

  /**
   * Formats customers sorted by identifier.
   *
   * @return newline-separated customer information
   */
  public String getCustomersInfoByIdOrder() {
    List<Customer> sortedCustomers = new ArrayList<>(customerList);
    sortedCustomers.sort(Comparator.comparingLong(Customer::getIdNumber));
    return formatCustomers(sortedCustomers);
  }

  /**
   * Formats customers sorted by name and then identifier.
   *
   * @return newline-separated customer information
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> sortedCustomers = new ArrayList<>(customerList);
    sortedCustomers.sort(Comparator.comparing(Customer::getFullName)
        .thenComparingLong(Customer::getIdNumber));
    return formatCustomers(sortedCustomers);
  }

  private String formatCustomers(List<Customer> customers) {
    StringBuilder result = new StringBuilder();
    for (Customer customer : customers) {
      if (result.length() > 0) {
        result.append('\n');
      }
      result.append(customer.getCustomerInfo());
    }
    return result.toString();
  }

  /**
   * Runs a small bank-system demonstration.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    LOGGER.info("Bank system started");
    CheckingAccount account = new CheckingAccount(10001L, 10000.0);
    account.deposit(2500.0);
    LOGGER.info("Demo transaction history:\n{}", account.getTransactionHistory());
    LOGGER.info("Bank system stopped");
  }
}
