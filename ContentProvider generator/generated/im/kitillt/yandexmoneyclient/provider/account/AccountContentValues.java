package im.kirillt.yandexmoneyclient.provider.account;

import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import im.kirillt.yandexmoneyclient.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code account} table.
 */
public class AccountContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return AccountColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable AccountSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public AccountContentValues putAccountnumber(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("accountnumber must not be null");
        mContentValues.put(AccountColumns.ACCOUNTNUMBER, value);
        return this;
    }


    public AccountContentValues putAccountusername(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("accountusername must not be null");
        mContentValues.put(AccountColumns.ACCOUNTUSERNAME, value);
        return this;
    }


    /**
     * real type: BigDecimal
     */
    public AccountContentValues putBalance(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("balance must not be null");
        mContentValues.put(AccountColumns.BALANCE, value);
        return this;
    }


    /**
     * real type: BigDecimal
     */
    public AccountContentValues putBalancehold(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("balancehold must not be null");
        mContentValues.put(AccountColumns.BALANCEHOLD, value);
        return this;
    }


    /**
     * avatar image file location
     */
    public AccountContentValues putAvatar(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("avatar must not be null");
        mContentValues.put(AccountColumns.AVATAR, value);
        return this;
    }

}
