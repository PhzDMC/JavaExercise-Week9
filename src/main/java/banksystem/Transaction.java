package banksystem;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory

/**
 * Đại diện cho một giao dịch trong hệ thống ngân hàng.
 * Đã được Refactor theo chuẩn Google Java Style và sử dụng SLF4J.
 */
public class Transaction {
  // Khai báo Logger chuẩn SLF4J
  private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type           Loại giao dịch
   * @param amount         Số tiền giao dịch
   * @param initialBalance Số dư ban đầu
   * @param finalBalance   Số dư sau giao dịch
   */

  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  public double getFinalBalance() {
    return finalBalance;
  }

  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Chuyển đổi mã loại giao dịch sang chuỗi ký tự mô tả.
   * Đã sửa lỗi Naming: get_type_string -> getTypeString (camelCase).
   *
   * @param transactionType Mã loại giao dịch
   * @return Chuỗi mô tả loại giao dịch
   */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Lấy thông tin tóm tắt của giao dịch.
   * Đã sửa lỗi LineLength và WhitespaceAround.
   *
   * @return Chuỗi tóm tắt giao dịch
   */
  public String getTransactionSummary() {
    logger.debug("Tiến hành tạo chuỗi tóm tắt cho giao dịch loại: {}", this.type);
    return String.format(
        Locale.US,
        "- Kiểu giao dịch: %s. Số dư ban đầu: $%.2f. Số tiền: $%.2f. Số dư cuối: $%.2f.",
        getTypeString(type),
        initialBalance,
        amount,
        finalBalance);
  }
}
