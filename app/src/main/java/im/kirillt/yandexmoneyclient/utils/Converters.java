package im.kirillt.yandexmoneyclient.utils;

import java.math.BigDecimal;

public class Converters {
    public Converters() {
    }

    public static String stringOrEmpty(String s) {
        return s == null ? "" : s;
    }

    public static BigDecimal bigDecimalOrZero(BigDecimal bd) {
        return bd == null ? new BigDecimal(0) : bd;
    }

    public static BigDecimal bigDecimalOrZero(String string) {
        BigDecimal rv = BigDecimal.ZERO;
        try {
            rv = new BigDecimal(string);
        } catch (NumberFormatException | NullPointerException ignored) {}
        return rv;
    }

    public static String bigDecimalToAmountString(BigDecimal bd) {
        return bd.setScale(2, BigDecimal.ROUND_CEILING).toString();
    }
}
