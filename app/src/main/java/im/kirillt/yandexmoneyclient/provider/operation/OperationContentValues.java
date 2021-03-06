package im.kirillt.yandexmoneyclient.provider.operation;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import im.kirillt.yandexmoneyclient.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code operation} table.
 */
public class OperationContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return OperationColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable OperationSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public OperationContentValues putOperationid(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("operationid must not be null");
        mContentValues.put(OperationColumns.OPERATIONID, value);
        return this;
    }


    public OperationContentValues putStatus(@NonNull status value) {
        if (value == null) throw new IllegalArgumentException("status must not be null");
        mContentValues.put(OperationColumns.STATUS, value.ordinal());
        return this;
    }


    public OperationContentValues putDirection(@NonNull direction value) {
        if (value == null) throw new IllegalArgumentException("direction must not be null");
        mContentValues.put(OperationColumns.DIRECTION, value.ordinal());
        return this;
    }


    /**
     * real type: BigDecimal
     */
    public OperationContentValues putAmount(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("amount must not be null");
        mContentValues.put(OperationColumns.AMOUNT, value);
        return this;
    }


    /**
     * real type: BigDecimal
     */
    public OperationContentValues putAmountdue(@Nullable String value) {
        mContentValues.put(OperationColumns.AMOUNTDUE, value);
        return this;
    }

    public OperationContentValues putAmountdueNull() {
        mContentValues.putNull(OperationColumns.AMOUNTDUE);
        return this;
    }

    /**
     * real type: BigDecimal
     */
    public OperationContentValues putFee(@Nullable String value) {
        mContentValues.put(OperationColumns.FEE, value);
        return this;
    }

    public OperationContentValues putFeeNull() {
        mContentValues.putNull(OperationColumns.FEE);
        return this;
    }

    /**
     * unix time here, real type: DateTime
     */
    public OperationContentValues putDatetime(long value) {
        mContentValues.put(OperationColumns.DATETIME, value);
        return this;
    }


    public OperationContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(OperationColumns.TITLE, value);
        return this;
    }


    public OperationContentValues putSender(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("sender must not be null");
        mContentValues.put(OperationColumns.SENDER, value);
        return this;
    }


    public OperationContentValues putRecipient(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("recipient must not be null");
        mContentValues.put(OperationColumns.RECIPIENT, value);
        return this;
    }


    public OperationContentValues putPayeeidentifiertype(@Nullable payeeIdentifierType value) {
        mContentValues.put(OperationColumns.PAYEEIDENTIFIERTYPE, value == null ? null : value.ordinal());
        return this;
    }

    public OperationContentValues putPayeeidentifiertypeNull() {
        mContentValues.putNull(OperationColumns.PAYEEIDENTIFIERTYPE);
        return this;
    }

    public OperationContentValues putMessage(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("message must not be null");
        mContentValues.put(OperationColumns.MESSAGE, value);
        return this;
    }


    public OperationContentValues putComment(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("comment must not be null");
        mContentValues.put(OperationColumns.COMMENT, value);
        return this;
    }


    public OperationContentValues putCodepro(boolean value) {
        mContentValues.put(OperationColumns.CODEPRO, value);
        return this;
    }


    public OperationContentValues putProtectioncode(@Nullable String value) {
        mContentValues.put(OperationColumns.PROTECTIONCODE, value);
        return this;
    }

    public OperationContentValues putProtectioncodeNull() {
        mContentValues.putNull(OperationColumns.PROTECTIONCODE);
        return this;
    }

    /**
     * unix time here, real type: DateTime
     */
    public OperationContentValues putExpires(@Nullable Long value) {
        mContentValues.put(OperationColumns.EXPIRES, value);
        return this;
    }

    public OperationContentValues putExpiresNull() {
        mContentValues.putNull(OperationColumns.EXPIRES);
        return this;
    }

    /**
     * unix time here, real type: DateTime
     */
    public OperationContentValues putAnswerdatetime(@Nullable Long value) {
        mContentValues.put(OperationColumns.ANSWERDATETIME, value);
        return this;
    }

    public OperationContentValues putAnswerdatetimeNull() {
        mContentValues.putNull(OperationColumns.ANSWERDATETIME);
        return this;
    }

    public OperationContentValues putLabel(@Nullable String value) {
        mContentValues.put(OperationColumns.LABEL, value);
        return this;
    }

    public OperationContentValues putLabelNull() {
        mContentValues.putNull(OperationColumns.LABEL);
        return this;
    }

    public OperationContentValues putDetails(@Nullable String value) {
        mContentValues.put(OperationColumns.DETAILS, value);
        return this;
    }

    public OperationContentValues putDetailsNull() {
        mContentValues.putNull(OperationColumns.DETAILS);
        return this;
    }

    public OperationContentValues putRepeatable(boolean value) {
        mContentValues.put(OperationColumns.REPEATABLE, value);
        return this;
    }


    public OperationContentValues putFavorite(boolean value) {
        mContentValues.put(OperationColumns.FAVORITE, value);
        return this;
    }


    /**
     * only *_TRANSFER_* values is used
     */
    public OperationContentValues putPaymenttype(@NonNull paymentType value) {
        if (value == null) throw new IllegalArgumentException("paymenttype must not be null");
        mContentValues.put(OperationColumns.PAYMENTTYPE, value.ordinal());
        return this;
    }

}
