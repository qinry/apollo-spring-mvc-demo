package demo;

import demo.util.CipherUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class CipherUtilTest {

    @Test
    public void test() throws Exception {
        String encrypt = "mysqlpwd";
        String encryption = CipherUtil.encrypt(encrypt);
        log.info("encryption:{}", encryption);
        String decryption = CipherUtil.decrypt(encryption);
        log.info("decryption:{}", decryption);
    }
}
