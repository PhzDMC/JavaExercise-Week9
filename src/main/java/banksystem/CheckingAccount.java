package banksystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho tài khoản vãng lai.
 * Đã Refactor: Tích hợp SLF4J cho quản lý ngoại lệ.
 */
public class CheckingAccount extends Account {
  private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);

  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING, amount, initialBalance, finalBalance);
      addTransaction(transaction);

      logger.info("Nạp tiền thành công tài khoản vãng lai: +{}", amount);
    } catch (BankException e) {
      // Sửa lỗi: Dùng SLF4J Error để ghi log thay vì System.out
      logger.error("Lỗi nạp tiền tài khoản vãng lai: {}", e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      double finalBalance = getBalance();
      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING, amount, initialBalance, finalBalance);
      addTransaction(transaction);

      logger.info("Rút tiền thành công tài khoản vãng lai: -{}", amount);
    } catch (BankException e) {
      // Sửa lỗi: Dùng SLF4J Error
      logger.error("Lỗi rút tiền tài khoản vãng lai: {}", e.getMessage());
    }
  }
}