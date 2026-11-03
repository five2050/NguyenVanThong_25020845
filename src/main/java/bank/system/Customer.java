package bank.system;

import java.util.ArrayList;
import java.util.List;

/**
 * A bank customer and the customer's accounts.
 */
public class Customer {
  private long idNumber;
  private String fullName;
  private List<Account> accountList = new ArrayList<>();

  /** Creates an empty customer. */
  public Customer() {
    this(0L, "");
  }

  /**
   * Creates a customer with an ID and name.
   *
   * @param idNumber customer identifier
   * @param fullName customer's full name
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
  }

  /**
   * Returns the customer identifier.
   *
   * @return customer identifier
   */
  public long getIdNumber() {
    return idNumber;
  }

  /**
   * Updates the customer identifier.
   *
   * @param idNumber new identifier
   */
  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  /**
   * Returns the customer's full name.
   *
   * @return full name
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Updates the customer's full name.
   *
   * @param fullName new full name
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Returns the customer's accounts.
   *
   * @return accounts in insertion order
   */
  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Replaces the customer's accounts.
   *
   * @param accounts accounts to store, or {@code null} for no accounts
   */
  public void setAccountList(List<Account> accounts) {
    accountList = accounts == null ? new ArrayList<>() : accounts;
  }

  /**
   * Adds an account if it is not already present.
   *
   * @param account account to add
   */
  public void addAccount(Account account) {
    if (account != null && !accountList.contains(account)) {
      accountList.add(account);
    }
  }

  /**
   * Removes an account.
   *
   * @param account account to remove
   */
  public void removeAccount(Account account) {
    accountList.remove(account);
  }

  /**
   * Formats the customer identity.
   *
   * @return customer information
   */
  public String getCustomerInfo() {
    return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
  }
}
