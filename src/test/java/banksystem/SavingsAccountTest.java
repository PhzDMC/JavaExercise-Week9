package banksystem;

import org.junit.jupiter.api.Test;

public class SavingsAccountTest {
  /*
  @Test
  public void testFilePathSystem() {
    // Cố tình dùng dấu gạch chéo ngược (\\) của Windows
    java.io.File file = new java.io.File("folder\\file.txt");

    // Trên Windows, nó hiểu "folder" là thư mục cha.
    // Nhưng trên Linux, nó coi nguyên cả cụm "folder\file.txt" là 1 cái tên file, nên thư mục cha sẽ bị null.
    org.junit.jupiter.api.Assertions.assertEquals("folder", file.getParent(), "Lỗi đường dẫn hệ điều hành!");
  }
  */

  @Test
  public void testFilePathSystem() {
    // Sử dụng Paths.get() để tự động xử lý dấu phân cách (/ hoặc \) theo hệ điều hành
    java.nio.file.Path path = java.nio.file.Paths.get("folder", "file.txt");
    java.io.File file = path.toFile();

    org.junit.jupiter.api.Assertions.assertEquals("folder", file.getParent(), "Đã sửa lỗi đường dẫn đa hệ điều hành!");
  }
}