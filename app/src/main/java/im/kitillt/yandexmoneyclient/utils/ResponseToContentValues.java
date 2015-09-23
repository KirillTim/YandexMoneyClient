package im.kitillt.yandexmoneyclient.utils;

import com.yandex.money.api.methods.AccountInfo;
import com.yandex.money.api.model.Operation;

import im.kitillt.yandexmoneyclient.provider.account.AccountContentValues;
import im.kitillt.yandexmoneyclient.provider.operation.OperationContentValues;
import im.kitillt.yandexmoneyclient.provider.operation.direction;
import im.kitillt.yandexmoneyclient.provider.operation.payeeIdentifierType;
import im.kitillt.yandexmoneyclient.provider.operation.paymentType;
import im.kitillt.yandexmoneyclient.provider.operation.status;

/**
 * Created by kirill on 19.09.15.
 */
public class ResponseToContentValues {
    public static AccountContentValues account(AccountInfo accountInfo) {
        return new AccountContentValues()
                .putAccountnumber(accountInfo.account)
                .putAccountusername("UserName")
                .putAvatar(accountInfo.avatar.url)
                .putBalance(accountInfo.balance.toString())
                .putBalancehold(accountInfo.balanceDetails.hold.toString());
    }

    public static OperationContentValues operation(Operation operation) {
        return new OperationContentValues()
                .putOperationid(operation.operationId)
                .putStatus(status.valueOf(operation.status.name()))
                .putDirection(direction.valueOf(operation.status.name()))
                .putAmount(operation.amount.toString())
                .putAmountdue(operation.amountDue.toString())
                .putFee(operation.fee.toString())
                .putDatetime(operation.datetime.getMillis() / 1000L)
                .putTitle(operation.title)
                .putSender(operation.sender)
                .putRecipient(operation.recipient)
                .putPayeeidentifiertype(payeeIdentifierType.valueOf(operation.recipientType.name()))
                .putMessage(operation.message)
                .putComment(operation.comment)
                .putCodepro(operation.codepro)
                .putProtectioncode(operation.protectionCode)
                .putExpires(operation.expires.getMillis() / 1000L)
                .putAnswerdatetime(operation.expires.getMillis() / 1000L)
                .putLabel(operation.label)
                .putDetails(operation.details)
                .putFavorite(operation.favorite)
                .putPaymenttype(paymentType.valueOf(operation.type.toString()));
    }
}
