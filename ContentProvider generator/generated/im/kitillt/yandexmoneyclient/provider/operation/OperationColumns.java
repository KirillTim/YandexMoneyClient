package im.kitillt.yandexmoneyclient.provider.operation;

import android.net.Uri;
import android.provider.BaseColumns;

import im.kitillt.yandexmoneyclient.provider.YMCContentProvider;
import im.kitillt.yandexmoneyclient.provider.account.AccountColumns;
import im.kitillt.yandexmoneyclient.provider.operation.OperationColumns;

/**
 * P2P payment operation.
 */
public class OperationColumns implements BaseColumns {
    public static final String TABLE_NAME = "operation";
    public static final Uri CONTENT_URI = Uri.parse(YMCContentProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String OPERATIONID = "operationId";

    public static final String STATUS = "status";

    public static final String DIRECTION = "direction";

    /**
     * real type: BigDecimal
     */
    public static final String AMOUNT = "amount";

    /**
     * real type: BigDecimal
     */
    public static final String AMOUNTDUE = "amountDue";

    /**
     * real type: BigDecimal
     */
    public static final String FEE = "fee";

    /**
     * real type: DateTime
     */
    public static final String DATETIME = "dateTime";

    public static final String TITLE = "title";

    public static final String SENDER = "sender";

    public static final String RECIPIENT = "recipient";

    public static final String PAYEEIDENTIFIERTYPE = "payeeIdentifierType";

    public static final String MESSAGE = "message";

    public static final String COMMENT = "comment";

    public static final String CODEPRO = "codepro";

    public static final String PROTECTIONCODE = "protectionCode";

    /**
     * real type: DateTime
     */
    public static final String EXPIRES = "expires";

    /**
     * real type: DateTime
     */
    public static final String ANSWERDATETIME = "answerDateTime";

    public static final String LABEL = "label";

    public static final String DETAILS = "details";

    public static final String REPEATABLE = "repeatable";

    public static final String FAVORITE = "favorite";

    public static final String PAYMENTTYPE = "paymentType";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            OPERATIONID,
            STATUS,
            DIRECTION,
            AMOUNT,
            AMOUNTDUE,
            FEE,
            DATETIME,
            TITLE,
            SENDER,
            RECIPIENT,
            PAYEEIDENTIFIERTYPE,
            MESSAGE,
            COMMENT,
            CODEPRO,
            PROTECTIONCODE,
            EXPIRES,
            ANSWERDATETIME,
            LABEL,
            DETAILS,
            REPEATABLE,
            FAVORITE,
            PAYMENTTYPE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(OPERATIONID) || c.contains("." + OPERATIONID)) return true;
            if (c.equals(STATUS) || c.contains("." + STATUS)) return true;
            if (c.equals(DIRECTION) || c.contains("." + DIRECTION)) return true;
            if (c.equals(AMOUNT) || c.contains("." + AMOUNT)) return true;
            if (c.equals(AMOUNTDUE) || c.contains("." + AMOUNTDUE)) return true;
            if (c.equals(FEE) || c.contains("." + FEE)) return true;
            if (c.equals(DATETIME) || c.contains("." + DATETIME)) return true;
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(SENDER) || c.contains("." + SENDER)) return true;
            if (c.equals(RECIPIENT) || c.contains("." + RECIPIENT)) return true;
            if (c.equals(PAYEEIDENTIFIERTYPE) || c.contains("." + PAYEEIDENTIFIERTYPE)) return true;
            if (c.equals(MESSAGE) || c.contains("." + MESSAGE)) return true;
            if (c.equals(COMMENT) || c.contains("." + COMMENT)) return true;
            if (c.equals(CODEPRO) || c.contains("." + CODEPRO)) return true;
            if (c.equals(PROTECTIONCODE) || c.contains("." + PROTECTIONCODE)) return true;
            if (c.equals(EXPIRES) || c.contains("." + EXPIRES)) return true;
            if (c.equals(ANSWERDATETIME) || c.contains("." + ANSWERDATETIME)) return true;
            if (c.equals(LABEL) || c.contains("." + LABEL)) return true;
            if (c.equals(DETAILS) || c.contains("." + DETAILS)) return true;
            if (c.equals(REPEATABLE) || c.contains("." + REPEATABLE)) return true;
            if (c.equals(FAVORITE) || c.contains("." + FAVORITE)) return true;
            if (c.equals(PAYMENTTYPE) || c.contains("." + PAYMENTTYPE)) return true;
        }
        return false;
    }

}
