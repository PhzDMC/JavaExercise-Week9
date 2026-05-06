package banksystem;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng cơ bản.
 * Đã Refactor: Chuẩn hóa tên biến, dùng StringBuilder, tích hợp SLF4J.
 */
public abstract class Account {
  private static final Logger logger = LoggerFactory.getLogger(Account.class);

  // Sửa lỗi: Hằng số phải là UPPER_SNAKE_CASE
  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  // Sửa lỗi: Tên biến rõ ràng, camelCase, không dùng gạch dưới
  private long accountNumber;
  private double balance;
  protected List<Transaction> transactionList;

  /**
   * Khởi tạo tài khoản với số tài khoản và số dư ban đầu.
   *
   * @param accountNumber Số tài khoản
   * @param balance       Số dư ban đầu
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactionList = new ArrayList<>();
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  protected void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  /**
   * Thiết lập danh sách giao dịch cho tài khoản.
   *
   * @param transactionList Danh sách giao dịch mới
   */
  public void setTransactionList(List<Transaction> transactionList) {
    // Sửa lỗi: Bổ sung đầy đủ ngoặc nhọn {} cho if-else
    if (transactionList == null) {
      this.transactionList = new ArrayList<>();
    } else {
      this.transactionList = transactionList;
    }
  }

  public abstract void deposit(double amount);

  public abstract void withdraw(double amount);

  /**
   * Xử lý logic nạp tiền cơ bản.
   *
   * @param amount Số tiền nạp
   * @throws InvalidFundingAmountException Nếu số tiền nạp <= 0
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  /**
   * Xử lý logic rút tiền cơ bản.
   *
   * @param amount Số tiền rút
   * @throws BankException Nếu số tiền rút không hợp lệ hoặc số dư không đủ
   */
  protected void doWithdrawing(double amount) throws BankException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Thêm một giao dịch vào danh sách.
   *
   * @param transaction Giao dịch cần thêm
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactionList.add(transaction);
    }
  }

  /**
   * Lấy lịch sử giao dịch của tài khoản.
   * Sửa lỗi: Thay thế String + bằng StringBuilder để tối ưu bộ nhớ.
   *
   * @return Chuỗi chứa lịch sử giao dịch
   */
  public String getTransactionHistory() {
    StringBuilder historyBuilder = new StringBuilder();
    historyBuilder.append("Lịch sử giao dịch của tài khoản ").append(accountNumber).append(":\n");

    for (int i = 0; i < transactionList.size(); i++) {
      historyBuilder.append(transactionList.get(i).getTransactionSummary());
      if (i < transactionList.size() - 1) {
        historyBuilder.append("\n");
      }
    }

    // Sửa lỗi: Thay System.out bằng SLF4J logger
    logger.debug("Đã lấy lịch sử cho tài khoản: {}", accountNumber);
    return historyBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

  @Override
  public int hashCode() {
    return (int) (accountNumber ^ (accountNumber >>> 32));
  }
}