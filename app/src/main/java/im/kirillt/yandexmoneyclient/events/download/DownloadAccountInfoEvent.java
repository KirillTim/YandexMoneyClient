package im.kirillt.yandexmoneyclient.events.download;

import android.content.Context;

import com.yandex.money.api.methods.AccountInfo;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns;
import im.kirillt.yandexmoneyclient.provider.account.AccountContentValues;
import im.kirillt.yandexmoneyclient.provider.account.AccountCursor;
import im.kirillt.yandexmoneyclient.provider.account.AccountSelection;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class DownloadAccountInfoEvent implements DownloadEvent {
    private Context context;
    private String login = null;

    public DownloadAccountInfoEvent(Context context, String login) {
        this.context = context;
        this.login = login;
    }

    public DownloadAccountInfoEvent(Context context) {
        this.context = context;
    }

    public void download() {
        try {
            YMCApplication.accountDownloadingStart();
            YMCApplication.auth2Session.enqueue(new AccountInfo.Request(), new ResponseReady<AccountInfo>() {
                @Override
                protected void failure(Exception exception) {
                    YMCApplication.accountDownloadingFinish();
                    exception.printStackTrace();
                    EventBus.getDefault().post(new AnyErrorEvent(exception));
                }

                @Override
                protected void response(AccountInfo response) {
                    AccountContentValues contentValues = new AccountContentValues();
                    contentValues.putBalance(response.balance.toString());
                    if (login != null) {
                        contentValues.putAccountusername(login);
                    } else {
                        contentValues.putAccountusername("");
                    }
                    if (response.balanceDetails != null) {
                        contentValues.putBalancehold(response.balanceDetails.hold.toString());
                    }
                    if (response.avatar != null) {
                        contentValues.putAvatar(response.avatar.url);
                    }
                    //TODO this
                    //yep, its crap. some magic bug is here and i don't write such a bad code often
                    AccountCursor ac = new AccountCursor(new AccountSelection()
                            .accountnumber(response.account).query(context.getContentResolver()));
                    ac.moveToFirst();
                    int r = context.getContentResolver().
                            update(AccountColumns.CONTENT_URI, contentValues.values(),
                                    new AccountSelection().accountnumber(response.account).sel(), null);
                    if (r == 0) {
                        new AccountSelection().accountnumberNot("").delete(context.getContentResolver());
                        contentValues.putAccountnumber(response.account);
                        if (login == null && ac.getCount() > 0) {
                            contentValues.putAccountusername(ac.getAccountusername());
                        }
                        contentValues.insert(context.getContentResolver());
                    }
                    YMCApplication.accountDownloadingFinish();
                    EventBus.getDefault().post(new SuccessAccountInfoEvent(response));
                }
            });
        } catch (Exception e) {
            YMCApplication.accountDownloadingFinish();
            e.printStackTrace();
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }
    }
}
