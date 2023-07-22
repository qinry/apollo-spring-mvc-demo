package demo.util;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

public class CipherUtil {
    private static String keyStr = "1234567812345678";

    private static SecretKey key;

    private static Cipher decryptCipher;

    private static Cipher encryptCipher;

    private static final String[] ENC_KEYS = { "password", "pwd", "secret" };

    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keyStr.getBytes(StandardCharsets.UTF_8));
            keyGenerator.init(secureRandom);
            key = keyGenerator.generateKey();
            encryptCipher = Cipher.getInstance("DES");
            decryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptProperty(String key, String value) {
        for (String encKey : Arrays.asList(ENC_KEYS)) {
            if (key.toLowerCase(Locale.ROOT).contains(encKey)) {
                return decrypt(value);
            }
        }
        return value;
    }

    public static String decrypt(String value) {
        try {
            byte[] bytes = Base64.getDecoder().decode(value);
            bytes = decryptCipher.doFinal(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return value;
        }
    }

    public static String encrypt(String value) {
        try {
            byte[] bytes = encryptCipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return value;
        }
    }
}
