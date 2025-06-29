package springbook.chatbotserver;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JasyptEncryptorTest {

  @Autowired
  private StringEncryptor encryptor;

  @Test
  void encryptTest() {
    String plainText = "http://localhost:5005/model/parse"; // 여기에 평문 API 키 입력
    String encrypted = encryptor.encrypt(plainText);
    System.out.println("🔐 Encrypted: ENC(" + encrypted + ")");
  }

  @Test
  void decryptTest() {
    String encryptedText = "26KJXNAMZ7cFJIjEp4XX2A=="; // 여기에 암호화된 텍스트 입력
    String decrypted = encryptor.decrypt(encryptedText);
    System.out.println("🔓 Decrypted: " + decrypted);
  }
}
