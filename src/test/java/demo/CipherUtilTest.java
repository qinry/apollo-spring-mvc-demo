package demo;

import demo.util.CipherUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
