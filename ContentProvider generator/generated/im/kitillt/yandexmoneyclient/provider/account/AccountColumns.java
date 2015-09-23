package im.kirillt.yandexmoneyclient.provider.account;

import android.net.Uri;
import android.provider.BaseColumns;

import im.kirillt.yandexmoneyclient.provider.YMCContentProvider;
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns;
import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns;

/**
 * Account info
 */
public class AccountColumns implements BaseColumns {
    public static final String TABLE_NAME = "account";
    public static final Uri CONTENT_URI = Uri.parse(YMCContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String ACCOUNTNUMBER = "accountNumber";

    public static final String ACCOUNTUSERNAME = "accountUserName";

    /**
     * real type: BigDecimal
     */
    public static final String BALANCE = "balance";

    /**
     * real type: BigDecimal
     */
    public static final String BALANCEHOLD = "balanceHold";

    /**
     * avatar image file location
     */
    public static final String AVATAR = "avatar";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ACCOUNTNUMBER,
            ACCOUNTUSERNAME,
            BALANCE,
            BALANCEHOLD,
            AVATAR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(ACCOUNTNUMBER) || c.contains("." + ACCOUNTNUMBER)) return true;
            if (c.equals(ACCOUNTUSERNAME) || c.contains("." + ACCOUNTUSERNAME)) return true;
            if (c.equals(BALANCE) || c.contains("." + BALANCE)) return true;
            if (c.equals(BALANCEHOLD) || c.contains("." + BALANCEHOLD)) return true;
            if (c.equals(AVATAR) || c.contains("." + AVATAR)) return true;
        }
        return false;
    }

}
