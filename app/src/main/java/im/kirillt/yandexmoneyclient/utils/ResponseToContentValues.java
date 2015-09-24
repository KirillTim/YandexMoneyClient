package im.kirillt.yandexmoneyclient.utils;

import com.yandex.money.api.methods.AccountInfo;
import com.yandex.money.api.model.Operation;

import java.math.BigDecimal;

import im.kirillt.yandexmoneyclient.provider.account.AccountContentValues;
import im.kirillt.yandexmoneyclient.provider.operation.OperationContentValues;
import im.kirillt.yandexmoneyclient.provider.operation.direction;
import im.kirillt.yandexmoneyclient.provider.operation.payeeIdentifierType;
import im.kirillt.yandexmoneyclient.provider.operation.paymentType;
import im.kirillt.yandexmoneyclient.provider.operation.status;

/**
 * Created by kirill on 19.09.15.
 */
public class ResponseToContentValues {
    public static AccountContentValues account(AccountInfo accountInfo) {
        AccountContentValues rv = new AccountContentValues();
        rv.putAccountnumber(accountInfo.account)
                .putAccountusername("UserName");
        if (accountInfo.avatar != null) {
            rv.putAvatar(stringOrEmpty(accountInfo.avatar.url));
        }
        rv.putBalance(accountInfo.balance.toString());
        if (accountInfo.balanceDetails != null) {
            rv.putBalancehold(bigDecimalOrZero(accountInfo.balanceDetails.hold).toString());
        }
        return rv;
    }

    public static OperationContentValues operation(Operation operation) {
        OperationContentValues rv = new OperationContentValues();
        rv.putOperationid(operation.operationId)
                .putStatus(status.valueOf(operation.status.name()))
                .putDirection(direction.valueOf(operation.direction.name()))
                .putAmount(bigDecimalOrZero(operation.amount).toString())
                .putAmountdue(bigDecimalOrZero(operation.amountDue).toString())
                .putFee(bigDecimalOrZero(operation.fee).toString())
                .putDatetime(operation.datetime.getMillis() / 1000L)
                .putTitle(stringOrEmpty(operation.title))
                .putSender(stringOrEmpty(operation.sender))
                .putRecipient(stringOrEmpty(operation.recipient));
        if (operation.recipientType != null) {
            rv.putPayeeidentifiertype(payeeIdentifierType.valueOf(operation.recipientType.name()));
        }
        rv.putMessage(stringOrEmpty(operation.message))
                .putComment(stringOrEmpty(operation.comment))
                .putCodepro(operation.codepro);
        if (operation.codepro) {
            rv.putProtectioncode(operation.protectionCode)
                    .putExpires(operation.expires.getMillis() / 1000L)
                    .putAnswerdatetime(operation.answerDatetime.getMillis() / 1000L);

        }
        rv.putLabel(stringOrEmpty(operation.label))
                .putDetails(stringOrEmpty(operation.details))
                .putRepeatable(operation.repeatable)
                .putFavorite(operation.favorite)
                .putPaymenttype(paymentType.valueOf(operation.type.toString()));
        return rv;
    }

    public static String stringOrEmpty(String s) {
        return s == null ? "" : s;
    }

    public static BigDecimal bigDecimalOrZero(BigDecimal bd) {
        return bd == null ? new BigDecimal(0) : bd;
    }
}
