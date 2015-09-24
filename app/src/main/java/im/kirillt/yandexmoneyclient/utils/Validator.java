package im.kirillt.yandexmoneyclient.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kirill on 24.09.15.
 */
public class Validator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static boolean validateAccountNumber(String accountNumber) {
        //TODO calc checksum
        for (int i = 0; i < accountNumber.length(); i++) {
            if (accountNumber.charAt(i) < '0' || accountNumber.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        //TODO improve validation
        return phoneNumber.charAt(0) == '+' && validateAccountNumber(phoneNumber.substring(1));
    }
}
