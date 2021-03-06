package im.kirillt.yandexmoneyclient.provider.operation;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import im.kirillt.yandexmoneyclient.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code operation} table.
 */
public class OperationCursor extends AbstractCursor implements OperationModel {
    public OperationCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(OperationColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code operationid} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getOperationid() {
        String res = getStringOrNull(OperationColumns.OPERATIONID);
        if (res == null)
            throw new NullPointerException("The value of 'operationid' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code status} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public status getStatus() {
        Integer intValue = getIntegerOrNull(OperationColumns.STATUS);
        if (intValue == null)
            throw new NullPointerException("The value of 'status' in the database was null, which is not allowed according to the model definition");
        return status.values()[intValue];
    }

    /**
     * Get the {@code direction} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public direction getDirection() {
        Integer intValue = getIntegerOrNull(OperationColumns.DIRECTION);
        if (intValue == null)
            throw new NullPointerException("The value of 'direction' in the database was null, which is not allowed according to the model definition");
        return direction.values()[intValue];
    }

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAmount() {
        String res = getStringOrNull(OperationColumns.AMOUNT);
        if (res == null)
            throw new NullPointerException("The value of 'amount' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * real type: BigDecimal
     * Can be {@code null}.
     */
    @Nullable
    public String getAmountdue() {
        String res = getStringOrNull(OperationColumns.AMOUNTDUE);
        return res;
    }

    /**
     * real type: BigDecimal
     * Can be {@code null}.
     */
    @Nullable
    public String getFee() {
        String res = getStringOrNull(OperationColumns.FEE);
        return res;
    }

    /**
     * unix time here, real type: DateTime
     */
    public long getDatetime() {
        Long res = getLongOrNull(OperationColumns.DATETIME);
        if (res == null)
            throw new NullPointerException("The value of 'datetime' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(OperationColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code sender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getSender() {
        String res = getStringOrNull(OperationColumns.SENDER);
        if (res == null)
            throw new NullPointerException("The value of 'sender' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code recipient} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getRecipient() {
        String res = getStringOrNull(OperationColumns.RECIPIENT);
        if (res == null)
            throw new NullPointerException("The value of 'recipient' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code payeeidentifiertype} value.
     * Can be {@code null}.
     */
    @Nullable
    public payeeIdentifierType getPayeeidentifiertype() {
        Integer intValue = getIntegerOrNull(OperationColumns.PAYEEIDENTIFIERTYPE);
        if (intValue == null) return null;
        return payeeIdentifierType.values()[intValue];
    }

    /**
     * Get the {@code message} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getMessage() {
        String res = getStringOrNull(OperationColumns.MESSAGE);
        if (res == null)
            throw new NullPointerException("The value of 'message' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code comment} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getComment() {
        String res = getStringOrNull(OperationColumns.COMMENT);
        if (res == null)
            throw new NullPointerException("The value of 'comment' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code codepro} value.
     */
    public boolean getCodepro() {
        Boolean res = getBooleanOrNull(OperationColumns.CODEPRO);
        if (res == null)
            throw new NullPointerException("The value of 'codepro' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code protectioncode} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getProtectioncode() {
        String res = getStringOrNull(OperationColumns.PROTECTIONCODE);
        return res;
    }

    /**
     * unix time here, real type: DateTime
     * Can be {@code null}.
     */
    @Nullable
    public Long getExpires() {
        Long res = getLongOrNull(OperationColumns.EXPIRES);
        return res;
    }

    /**
     * unix time here, real type: DateTime
     * Can be {@code null}.
     */
    @Nullable
    public Long getAnswerdatetime() {
        Long res = getLongOrNull(OperationColumns.ANSWERDATETIME);
        return res;
    }

    /**
     * Get the {@code label} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getLabel() {
        String res = getStringOrNull(OperationColumns.LABEL);
        return res;
    }

    /**
     * Get the {@code details} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDetails() {
        String res = getStringOrNull(OperationColumns.DETAILS);
        return res;
    }

    /**
     * Get the {@code repeatable} value.
     */
    public boolean getRepeatable() {
        Boolean res = getBooleanOrNull(OperationColumns.REPEATABLE);
        if (res == null)
            throw new NullPointerException("The value of 'repeatable' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code favorite} value.
     */
    public boolean getFavorite() {
        Boolean res = getBooleanOrNull(OperationColumns.FAVORITE);
        if (res == null)
            throw new NullPointerException("The value of 'favorite' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * only *_TRANSFER_* values is used
     * Cannot be {@code null}.
     */
    @NonNull
    public paymentType getPaymenttype() {
        Integer intValue = getIntegerOrNull(OperationColumns.PAYMENTTYPE);
        if (intValue == null)
            throw new NullPointerException("The value of 'paymenttype' in the database was null, which is not allowed according to the model definition");
        return paymentType.values()[intValue];
    }
}
