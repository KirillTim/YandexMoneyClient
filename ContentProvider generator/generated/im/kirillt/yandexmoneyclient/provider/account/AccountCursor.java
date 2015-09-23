package im.kirillt.yandexmoneyclient.provider.account;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import im.kirillt.yandexmoneyclient.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code account} table.
 */
public class AccountCursor extends AbstractCursor implements AccountModel {
    public AccountCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(AccountColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code accountnumber} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAccountnumber() {
        String res = getStringOrNull(AccountColumns.ACCOUNTNUMBER);
        if (res == null)
            throw new NullPointerException("The value of 'accountnumber' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code accountusername} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAccountusername() {
        String res = getStringOrNull(AccountColumns.ACCOUNTUSERNAME);
        if (res == null)
            throw new NullPointerException("The value of 'accountusername' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    public String getBalance() {
        String res = getStringOrNull(AccountColumns.BALANCE);
        if (res == null)
            throw new NullPointerException("The value of 'balance' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    public String getBalancehold() {
        String res = getStringOrNull(AccountColumns.BALANCEHOLD);
        if (res == null)
            throw new NullPointerException("The value of 'balancehold' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * avatar image file location
     * Can be {@code null}.
     */
    @Nullable
    public String getAvatar() {
        String res = getStringOrNull(AccountColumns.AVATAR);
        return res;
    }
}
