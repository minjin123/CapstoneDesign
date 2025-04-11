import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptEncryptorTest {

	@Autowired
	private StringEncryptor encryptor;

	@Test
	void encryptTest() {
		String plainText = "your-openai-api-key"; // 여기에 평문 API 키 입력
		String encrypted = encryptor.encrypt(plainText);
		System.out.println("🔐 Encrypted: ENC(" + encrypted + ")");
	}

	@Test
	void decryptTest() {
		String encryptedText = "aQp9d01VnLmnUQx+RhkEfw=="; // 암호화된 텍스트
		String decrypted = encryptor.decrypt(encryptedText);
		System.out.println("🔓 Decrypted: " + decrypted);
	}
}
