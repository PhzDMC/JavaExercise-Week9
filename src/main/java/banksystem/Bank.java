package banksystem;

// Liệt kê đích danh thư viện (Sửa lỗi Wildcard import)
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;




/**
 * Lớp đại diện cho ngân hàng, quản lý danh sách khách hàng.
 */
public class Bank {
  private static final Logger logger = LoggerFactory.getLogger(Bank.class);

  // Sửa lỗi Naming: c_list -> customerList
  private List<Customer> customerList;

  public Bank() {
    this.customerList = new ArrayList<>();
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Cập nhật danh sách khách hàng.
   *
   * @param customerList Danh sách khách hàng mới
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<>();
    } else {
      this.customerList = customerList;
    }
  }

  /**
   * Đọc dữ liệu khách hàng từ InputStream.
   * Đã Refactor: Giảm độ sâu của if lồng nhau, tích hợp SLF4J.
   *
   * @param inputStream Luồng dữ liệu đầu vào
   */
  public void readCustomerList(InputStream inputStream) {
    logger.info("Bắt đầu đọc dữ liệu khách hàng từ InputStream...");
    if (inputStream == null) {
      return;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      Customer current = null;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        // Dùng lệnh continue để "san phẳng" khối code, tránh if lồng nhau
        if (line.isEmpty()) {
          continue;
        }

        int lastSpaceIndex = line.lastIndexOf(' ');
        if (lastSpaceIndex <= 0) {
          continue;
        }

        String token = line.substring(lastSpaceIndex + 1).trim();
        if (token.matches("\\d{9}")) {
          String name = line.substring(0, lastSpaceIndex).trim();
          current = new Customer(Long.parseLong(token), name);
          customerList.add(current);
          logger.debug("Đã thêm khách hàng mới: {}", name);
        } else if (current != null) {
          String[] parts = line.split("\\s+");
          if (parts.length >= 3) {
            long accountNumber = Long.parseLong(parts[0]);
            double balance = Double.parseDouble(parts[2]);
            // Sử dụng hằng số từ class Account thay vì gõ "CHECKING" thủ công
            if (Account.CHECKING_TYPE.equals(parts[1])) {
              current.addAccount(new CheckingAccount(accountNumber, balance));
            } else if (Account.SAVINGS_TYPE.equals(parts[1])) {
              current.addAccount(new SavingsAccount(accountNumber, balance));
            }
          }
        }
      }
      logger.info("Đọc dữ liệu hoàn tất. Tổng số khách hàng: {}", customerList.size());
    } catch (IOException e) {
      // Bắt đích danh lỗi luồng dữ liệu (Input/Output)
      logger.error("Lỗi I/O khi đọc luồng dữ liệu khách hàng: {}", e.getMessage(), e);
    } catch (NumberFormatException e) {
      // Bắt đích danh lỗi ép kiểu số khi parse ID hoặc số dư
      logger.error("Lỗi sai định dạng số liệu trong tệp khách hàng: {}", e.getMessage(), e);
    }
  }

  /**
   * Lấy thông tin khách hàng sắp xếp theo ID.
   *
   * @return Chuỗi thông tin khách hàng
   */
  public String getCustomersInfoByIdOrder() {
    // Sử dụng Lambda thay cho Anonymous class để code ngắn gọn
    Collections.sort(customerList, (c1, c2) -> Long.compare(c1.getIdNumber(), c2.getIdNumber()));

    // Sử dụng StringBuilder thay cho String concatenation
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < customerList.size(); i++) {
      builder.append(customerList.get(i).getCustomerInfo());
      if (i < customerList.size() - 1) {
        builder.append("\n");
      }
    }
    return builder.toString();
  }

  /**
   * Lấy thông tin khách hàng sắp xếp theo Tên.
   *
   * @return Chuỗi thông tin khách hàng
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> copy = new ArrayList<>(customerList);
    copy.sort(Comparator.comparing(Customer::getFullName).thenComparingLong(Customer::getIdNumber));

    StringBuilder builder = new StringBuilder();
    for (Customer c : copy) {
      builder.append(c.getCustomerInfo()).append("\n");
    }
    return builder.toString().trim();
  }
}