package banksystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho tài khoản tiết kiệm.
 * Đã Refactor: Chuẩn hóa Magic Numbers, tích hợp SLF4J.
 */
public class SavingsAccount extends Account {
  private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

  // Khai báo hằng số để loại bỏ Magic Numbers
  private static final double MAX_WITHDRAW_AMOUNT = 1000.0;
  private static final double MIN_BALANCE = 5000.0;

  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    // Thay thế System.err bằng SLF4J (Dùng mức INFO vì đây là hành động chính của hệ thống)
    logger.info("Bắt đầu xử lý nạp tiền cho tài khoản tiết kiệm: {}", getAccountNumber());

    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();

      // Sử dụng hằng số thay vì số '3'
      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance);
      addTransaction(transaction);

      logger.info("Nạp tiền thành công: +{}. Số dư mới: {}", amount, finalBalance);
    } catch (BankException e) {
      // Sửa lỗi: Bắt đúng Exception của nghiệp vụ ngân hàng thay vì bắt Exception chung chung
      logger.error("Lỗi khi nạp tiền vào tài khoản tiết kiệm: {}", e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAW_AMOUNT) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();

      // Sử dụng hằng số thay vì số '4'
      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance);
      addTransaction(transaction);

      logger.info("Rút tiền thành công từ tài khoản tiết kiệm: -{}. Số dư mới: {}",
          amount, finalBalance);
    } catch (BankException e) {
      // Sửa lỗi: Log đầy đủ lỗi với SLF4J
      logger.error("Lỗi khi rút tiền tài khoản tiết kiệm {}: {}",
          getAccountNumber(), e.getMessage());
    }
  }
}