package im.kitillt.yandexmoneyclient.provider.operation;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import im.kitillt.yandexmoneyclient.provider.base.AbstractSelection;

/**
 * Selection for the {@code operation} table.
 */
public class OperationSelection extends AbstractSelection<OperationSelection> {
    @Override
    protected Uri baseUri() {
        return OperationColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code OperationCursor} object, which is positioned before the first entry, or null.
     */
    public OperationCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new OperationCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public OperationCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public OperationCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public OperationSelection id(long... value) {
        addEquals("operation." + OperationColumns._ID, toObjectArray(value));
        return this;
    }

    public OperationSelection operationid(String... value) {
        addEquals(OperationColumns.OPERATIONID, value);
        return this;
    }

    public OperationSelection operationidNot(String... value) {
        addNotEquals(OperationColumns.OPERATIONID, value);
        return this;
    }

    public OperationSelection operationidLike(String... value) {
        addLike(OperationColumns.OPERATIONID, value);
        return this;
    }

    public OperationSelection operationidContains(String... value) {
        addContains(OperationColumns.OPERATIONID, value);
        return this;
    }

    public OperationSelection operationidStartsWith(String... value) {
        addStartsWith(OperationColumns.OPERATIONID, value);
        return this;
    }

    public OperationSelection operationidEndsWith(String... value) {
        addEndsWith(OperationColumns.OPERATIONID, value);
        return this;
    }

    public OperationSelection status(status... value) {
        addEquals(OperationColumns.STATUS, value);
        return this;
    }

    public OperationSelection statusNot(status... value) {
        addNotEquals(OperationColumns.STATUS, value);
        return this;
    }


    public OperationSelection direction(direction... value) {
        addEquals(OperationColumns.DIRECTION, value);
        return this;
    }

    public OperationSelection directionNot(direction... value) {
        addNotEquals(OperationColumns.DIRECTION, value);
        return this;
    }


    public OperationSelection amount(String... value) {
        addEquals(OperationColumns.AMOUNT, value);
        return this;
    }

    public OperationSelection amountNot(String... value) {
        addNotEquals(OperationColumns.AMOUNT, value);
        return this;
    }

    public OperationSelection amountLike(String... value) {
        addLike(OperationColumns.AMOUNT, value);
        return this;
    }

    public OperationSelection amountContains(String... value) {
        addContains(OperationColumns.AMOUNT, value);
        return this;
    }

    public OperationSelection amountStartsWith(String... value) {
        addStartsWith(OperationColumns.AMOUNT, value);
        return this;
    }

    public OperationSelection amountEndsWith(String... value) {
        addEndsWith(OperationColumns.AMOUNT, value);
        return this;
    }

    public OperationSelection amountdue(String... value) {
        addEquals(OperationColumns.AMOUNTDUE, value);
        return this;
    }

    public OperationSelection amountdueNot(String... value) {
        addNotEquals(OperationColumns.AMOUNTDUE, value);
        return this;
    }

    public OperationSelection amountdueLike(String... value) {
        addLike(OperationColumns.AMOUNTDUE, value);
        return this;
    }

    public OperationSelection amountdueContains(String... value) {
        addContains(OperationColumns.AMOUNTDUE, value);
        return this;
    }

    public OperationSelection amountdueStartsWith(String... value) {
        addStartsWith(OperationColumns.AMOUNTDUE, value);
        return this;
    }

    public OperationSelection amountdueEndsWith(String... value) {
        addEndsWith(OperationColumns.AMOUNTDUE, value);
        return this;
    }

    public OperationSelection fee(String... value) {
        addEquals(OperationColumns.FEE, value);
        return this;
    }

    public OperationSelection feeNot(String... value) {
        addNotEquals(OperationColumns.FEE, value);
        return this;
    }

    public OperationSelection feeLike(String... value) {
        addLike(OperationColumns.FEE, value);
        return this;
    }

    public OperationSelection feeContains(String... value) {
        addContains(OperationColumns.FEE, value);
        return this;
    }

    public OperationSelection feeStartsWith(String... value) {
        addStartsWith(OperationColumns.FEE, value);
        return this;
    }

    public OperationSelection feeEndsWith(String... value) {
        addEndsWith(OperationColumns.FEE, value);
        return this;
    }

    public OperationSelection datetime(String... value) {
        addEquals(OperationColumns.DATETIME, value);
        return this;
    }

    public OperationSelection datetimeNot(String... value) {
        addNotEquals(OperationColumns.DATETIME, value);
        return this;
    }

    public OperationSelection datetimeLike(String... value) {
        addLike(OperationColumns.DATETIME, value);
        return this;
    }

    public OperationSelection datetimeContains(String... value) {
        addContains(OperationColumns.DATETIME, value);
        return this;
    }

    public OperationSelection datetimeStartsWith(String... value) {
        addStartsWith(OperationColumns.DATETIME, value);
        return this;
    }

    public OperationSelection datetimeEndsWith(String... value) {
        addEndsWith(OperationColumns.DATETIME, value);
        return this;
    }

    public OperationSelection title(String... value) {
        addEquals(OperationColumns.TITLE, value);
        return this;
    }

    public OperationSelection titleNot(String... value) {
        addNotEquals(OperationColumns.TITLE, value);
        return this;
    }

    public OperationSelection titleLike(String... value) {
        addLike(OperationColumns.TITLE, value);
        return this;
    }

    public OperationSelection titleContains(String... value) {
        addContains(OperationColumns.TITLE, value);
        return this;
    }

    public OperationSelection titleStartsWith(String... value) {
        addStartsWith(OperationColumns.TITLE, value);
        return this;
    }

    public OperationSelection titleEndsWith(String... value) {
        addEndsWith(OperationColumns.TITLE, value);
        return this;
    }

    public OperationSelection sender(String... value) {
        addEquals(OperationColumns.SENDER, value);
        return this;
    }

    public OperationSelection senderNot(String... value) {
        addNotEquals(OperationColumns.SENDER, value);
        return this;
    }

    public OperationSelection senderLike(String... value) {
        addLike(OperationColumns.SENDER, value);
        return this;
    }

    public OperationSelection senderContains(String... value) {
        addContains(OperationColumns.SENDER, value);
        return this;
    }

    public OperationSelection senderStartsWith(String... value) {
        addStartsWith(OperationColumns.SENDER, value);
        return this;
    }

    public OperationSelection senderEndsWith(String... value) {
        addEndsWith(OperationColumns.SENDER, value);
        return this;
    }

    public OperationSelection recipient(String... value) {
        addEquals(OperationColumns.RECIPIENT, value);
        return this;
    }

    public OperationSelection recipientNot(String... value) {
        addNotEquals(OperationColumns.RECIPIENT, value);
        return this;
    }

    public OperationSelection recipientLike(String... value) {
        addLike(OperationColumns.RECIPIENT, value);
        return this;
    }

    public OperationSelection recipientContains(String... value) {
        addContains(OperationColumns.RECIPIENT, value);
        return this;
    }

    public OperationSelection recipientStartsWith(String... value) {
        addStartsWith(OperationColumns.RECIPIENT, value);
        return this;
    }

    public OperationSelection recipientEndsWith(String... value) {
        addEndsWith(OperationColumns.RECIPIENT, value);
        return this;
    }

    public OperationSelection payeeidentifiertype(payeeIdentifierType... value) {
        addEquals(OperationColumns.PAYEEIDENTIFIERTYPE, value);
        return this;
    }

    public OperationSelection payeeidentifiertypeNot(payeeIdentifierType... value) {
        addNotEquals(OperationColumns.PAYEEIDENTIFIERTYPE, value);
        return this;
    }


    public OperationSelection message(String... value) {
        addEquals(OperationColumns.MESSAGE, value);
        return this;
    }

    public OperationSelection messageNot(String... value) {
        addNotEquals(OperationColumns.MESSAGE, value);
        return this;
    }

    public OperationSelection messageLike(String... value) {
        addLike(OperationColumns.MESSAGE, value);
        return this;
    }

    public OperationSelection messageContains(String... value) {
        addContains(OperationColumns.MESSAGE, value);
        return this;
    }

    public OperationSelection messageStartsWith(String... value) {
        addStartsWith(OperationColumns.MESSAGE, value);
        return this;
    }

    public OperationSelection messageEndsWith(String... value) {
        addEndsWith(OperationColumns.MESSAGE, value);
        return this;
    }

    public OperationSelection comment(String... value) {
        addEquals(OperationColumns.COMMENT, value);
        return this;
    }

    public OperationSelection commentNot(String... value) {
        addNotEquals(OperationColumns.COMMENT, value);
        return this;
    }

    public OperationSelection commentLike(String... value) {
        addLike(OperationColumns.COMMENT, value);
        return this;
    }

    public OperationSelection commentContains(String... value) {
        addContains(OperationColumns.COMMENT, value);
        return this;
    }

    public OperationSelection commentStartsWith(String... value) {
        addStartsWith(OperationColumns.COMMENT, value);
        return this;
    }

    public OperationSelection commentEndsWith(String... value) {
        addEndsWith(OperationColumns.COMMENT, value);
        return this;
    }

    public OperationSelection codepro(boolean value) {
        addEquals(OperationColumns.CODEPRO, toObjectArray(value));
        return this;
    }

    public OperationSelection protectioncode(String... value) {
        addEquals(OperationColumns.PROTECTIONCODE, value);
        return this;
    }

    public OperationSelection protectioncodeNot(String... value) {
        addNotEquals(OperationColumns.PROTECTIONCODE, value);
        return this;
    }

    public OperationSelection protectioncodeLike(String... value) {
        addLike(OperationColumns.PROTECTIONCODE, value);
        return this;
    }

    public OperationSelection protectioncodeContains(String... value) {
        addContains(OperationColumns.PROTECTIONCODE, value);
        return this;
    }

    public OperationSelection protectioncodeStartsWith(String... value) {
        addStartsWith(OperationColumns.PROTECTIONCODE, value);
        return this;
    }

    public OperationSelection protectioncodeEndsWith(String... value) {
        addEndsWith(OperationColumns.PROTECTIONCODE, value);
        return this;
    }

    public OperationSelection expires(String... value) {
        addEquals(OperationColumns.EXPIRES, value);
        return this;
    }

    public OperationSelection expiresNot(String... value) {
        addNotEquals(OperationColumns.EXPIRES, value);
        return this;
    }

    public OperationSelection expiresLike(String... value) {
        addLike(OperationColumns.EXPIRES, value);
        return this;
    }

    public OperationSelection expiresContains(String... value) {
        addContains(OperationColumns.EXPIRES, value);
        return this;
    }

    public OperationSelection expiresStartsWith(String... value) {
        addStartsWith(OperationColumns.EXPIRES, value);
        return this;
    }

    public OperationSelection expiresEndsWith(String... value) {
        addEndsWith(OperationColumns.EXPIRES, value);
        return this;
    }

    public OperationSelection answerdatetime(String... value) {
        addEquals(OperationColumns.ANSWERDATETIME, value);
        return this;
    }

    public OperationSelection answerdatetimeNot(String... value) {
        addNotEquals(OperationColumns.ANSWERDATETIME, value);
        return this;
    }

    public OperationSelection answerdatetimeLike(String... value) {
        addLike(OperationColumns.ANSWERDATETIME, value);
        return this;
    }

    public OperationSelection answerdatetimeContains(String... value) {
        addContains(OperationColumns.ANSWERDATETIME, value);
        return this;
    }

    public OperationSelection answerdatetimeStartsWith(String... value) {
        addStartsWith(OperationColumns.ANSWERDATETIME, value);
        return this;
    }

    public OperationSelection answerdatetimeEndsWith(String... value) {
        addEndsWith(OperationColumns.ANSWERDATETIME, value);
        return this;
    }

    public OperationSelection label(String... value) {
        addEquals(OperationColumns.LABEL, value);
        return this;
    }

    public OperationSelection labelNot(String... value) {
        addNotEquals(OperationColumns.LABEL, value);
        return this;
    }

    public OperationSelection labelLike(String... value) {
        addLike(OperationColumns.LABEL, value);
        return this;
    }

    public OperationSelection labelContains(String... value) {
        addContains(OperationColumns.LABEL, value);
        return this;
    }

    public OperationSelection labelStartsWith(String... value) {
        addStartsWith(OperationColumns.LABEL, value);
        return this;
    }

    public OperationSelection labelEndsWith(String... value) {
        addEndsWith(OperationColumns.LABEL, value);
        return this;
    }

    public OperationSelection details(String... value) {
        addEquals(OperationColumns.DETAILS, value);
        return this;
    }

    public OperationSelection detailsNot(String... value) {
        addNotEquals(OperationColumns.DETAILS, value);
        return this;
    }

    public OperationSelection detailsLike(String... value) {
        addLike(OperationColumns.DETAILS, value);
        return this;
    }

    public OperationSelection detailsContains(String... value) {
        addContains(OperationColumns.DETAILS, value);
        return this;
    }

    public OperationSelection detailsStartsWith(String... value) {
        addStartsWith(OperationColumns.DETAILS, value);
        return this;
    }

    public OperationSelection detailsEndsWith(String... value) {
        addEndsWith(OperationColumns.DETAILS, value);
        return this;
    }

    public OperationSelection repeatable(boolean value) {
        addEquals(OperationColumns.REPEATABLE, toObjectArray(value));
        return this;
    }

    public OperationSelection favorite(boolean value) {
        addEquals(OperationColumns.FAVORITE, toObjectArray(value));
        return this;
    }

    public OperationSelection paymenttype(paymentType... value) {
        addEquals(OperationColumns.PAYMENTTYPE, value);
        return this;
    }

    public OperationSelection paymenttypeNot(paymentType... value) {
        addNotEquals(OperationColumns.PAYMENTTYPE, value);
        return this;
    }

}
