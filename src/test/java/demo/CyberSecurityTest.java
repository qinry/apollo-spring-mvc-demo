package demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class CyberSecurityTest {

    @Test
    public void test() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String keyStr = "1234567812345678";
        String encrypt = "mysqlpw";
        Cipher cipher = Cipher.getInstance("DES");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(keyStr.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(secureRandom);
        SecretKey key = keyGenerator.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(encrypt.getBytes(StandardCharsets.UTF_8));
        String result = Base64.getEncoder().encodeToString(bytes);
        log.info("result:{}", result);

        bytes = Base64.getDecoder().decode(result);
        cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        bytes = cipher.doFinal(bytes);
        log.info("decrypt:{}", new String(bytes, StandardCharsets.UTF_8));
    }
}
