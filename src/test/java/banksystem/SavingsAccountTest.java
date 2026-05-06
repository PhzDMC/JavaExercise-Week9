package banksystem;

import org.junit.jupiter.api.Test;

public class SavingsAccountTest {

  @Test
  public void testFilePathSystem() {
    // Cố tình dùng dấu gạch chéo ngược (\\) của Windows
    java.io.File file = new java.io.File("folder\\file.txt");

    // Trên Windows, nó hiểu "folder" là thư mục cha.
    // NHƯNG trên Linux, nó coi nguyên cả cụm "folder\file.txt" là 1 cái tên file, nên thư mục cha sẽ bị null.
    org.junit.jupiter.api.Assertions.assertEquals("folder", file.getParent(), "Lỗi đường dẫn hệ điều hành!");
  }
}