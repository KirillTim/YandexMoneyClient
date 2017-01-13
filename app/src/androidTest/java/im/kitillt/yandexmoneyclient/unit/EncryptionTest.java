package im.kitillt.yandexmoneyclient.unit;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;

import im.kirillt.yandexmoneyclient.Encryption;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class EncryptionTest {

    final static String PASSWORD = "12345678";
    final static String TOKEN = "TOKEN=12345678SUCHATOKEN";

    @Test
    public void correctPasswordTest() {

        BigInteger b1 = Encryption.passMD5(PASSWORD);
        String token = Encryption.encryptToken(TOKEN, PASSWORD).toString();
        assertEquals(TOKEN, Encryption.decryptToken(token, PASSWORD));
    }

    @Test
    public void wrongPasswordTest() {
        String wrongPassword = "12345677";
        assertNotEquals(Encryption.passMD5(PASSWORD), Encryption.passMD5(wrongPassword));
        String token = Encryption.encryptToken(TOKEN, PASSWORD).toString();
        assertNotEquals(TOKEN, Encryption.decryptToken(token, wrongPassword));
    }
}
