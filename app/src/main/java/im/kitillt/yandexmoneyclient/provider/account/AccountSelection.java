package im.kitillt.yandexmoneyclient.provider.account;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import im.kitillt.yandexmoneyclient.provider.base.AbstractSelection;

/**
 * Selection for the {@code account} table.
 */
public class AccountSelection extends AbstractSelection<AccountSelection> {
    @Override
    protected Uri baseUri() {
        return AccountColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code AccountCursor} object, which is positioned before the first entry, or null.
     */
    public AccountCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new AccountCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public AccountCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public AccountCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public AccountSelection id(long... value) {
        addEquals("account." + AccountColumns._ID, toObjectArray(value));
        return this;
    }

    public AccountSelection accountnumber(String... value) {
        addEquals(AccountColumns.ACCOUNTNUMBER, value);
        return this;
    }

    public AccountSelection accountnumberNot(String... value) {
        addNotEquals(AccountColumns.ACCOUNTNUMBER, value);
        return this;
    }

    public AccountSelection accountnumberLike(String... value) {
        addLike(AccountColumns.ACCOUNTNUMBER, value);
        return this;
    }

    public AccountSelection accountnumberContains(String... value) {
        addContains(AccountColumns.ACCOUNTNUMBER, value);
        return this;
    }

    public AccountSelection accountnumberStartsWith(String... value) {
        addStartsWith(AccountColumns.ACCOUNTNUMBER, value);
        return this;
    }

    public AccountSelection accountnumberEndsWith(String... value) {
        addEndsWith(AccountColumns.ACCOUNTNUMBER, value);
        return this;
    }

    public AccountSelection accountusername(String... value) {
        addEquals(AccountColumns.ACCOUNTUSERNAME, value);
        return this;
    }

    public AccountSelection accountusernameNot(String... value) {
        addNotEquals(AccountColumns.ACCOUNTUSERNAME, value);
        return this;
    }

    public AccountSelection accountusernameLike(String... value) {
        addLike(AccountColumns.ACCOUNTUSERNAME, value);
        return this;
    }

    public AccountSelection accountusernameContains(String... value) {
        addContains(AccountColumns.ACCOUNTUSERNAME, value);
        return this;
    }

    public AccountSelection accountusernameStartsWith(String... value) {
        addStartsWith(AccountColumns.ACCOUNTUSERNAME, value);
        return this;
    }

    public AccountSelection accountusernameEndsWith(String... value) {
        addEndsWith(AccountColumns.ACCOUNTUSERNAME, value);
        return this;
    }

    public AccountSelection balance(String... value) {
        addEquals(AccountColumns.BALANCE, value);
        return this;
    }

    public AccountSelection balanceNot(String... value) {
        addNotEquals(AccountColumns.BALANCE, value);
        return this;
    }

    public AccountSelection balanceLike(String... value) {
        addLike(AccountColumns.BALANCE, value);
        return this;
    }

    public AccountSelection balanceContains(String... value) {
        addContains(AccountColumns.BALANCE, value);
        return this;
    }

    public AccountSelection balanceStartsWith(String... value) {
        addStartsWith(AccountColumns.BALANCE, value);
        return this;
    }

    public AccountSelection balanceEndsWith(String... value) {
        addEndsWith(AccountColumns.BALANCE, value);
        return this;
    }

    public AccountSelection balancehold(String... value) {
        addEquals(AccountColumns.BALANCEHOLD, value);
        return this;
    }

    public AccountSelection balanceholdNot(String... value) {
        addNotEquals(AccountColumns.BALANCEHOLD, value);
        return this;
    }

    public AccountSelection balanceholdLike(String... value) {
        addLike(AccountColumns.BALANCEHOLD, value);
        return this;
    }

    public AccountSelection balanceholdContains(String... value) {
        addContains(AccountColumns.BALANCEHOLD, value);
        return this;
    }

    public AccountSelection balanceholdStartsWith(String... value) {
        addStartsWith(AccountColumns.BALANCEHOLD, value);
        return this;
    }

    public AccountSelection balanceholdEndsWith(String... value) {
        addEndsWith(AccountColumns.BALANCEHOLD, value);
        return this;
    }

    public AccountSelection avatar(String... value) {
        addEquals(AccountColumns.AVATAR, value);
        return this;
    }

    public AccountSelection avatarNot(String... value) {
        addNotEquals(AccountColumns.AVATAR, value);
        return this;
    }

    public AccountSelection avatarLike(String... value) {
        addLike(AccountColumns.AVATAR, value);
        return this;
    }

    public AccountSelection avatarContains(String... value) {
        addContains(AccountColumns.AVATAR, value);
        return this;
    }

    public AccountSelection avatarStartsWith(String... value) {
        addStartsWith(AccountColumns.AVATAR, value);
        return this;
    }

    public AccountSelection avatarEndsWith(String... value) {
        addEndsWith(AccountColumns.AVATAR, value);
        return this;
    }
}
