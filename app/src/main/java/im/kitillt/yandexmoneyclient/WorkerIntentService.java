package im.kitillt.yandexmoneyclient;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.yandex.money.api.methods.AccountInfo;
import com.yandex.money.api.net.OnResponseReady;

import org.joda.time.DateTime;

import java.io.IOException;

import im.kitillt.yandexmoneyclient.provider.account.AccountSelection;
import im.kitillt.yandexmoneyclient.utils.ResponseFailureCallback;
import im.kitillt.yandexmoneyclient.utils.ResponseReady;
import im.kitillt.yandexmoneyclient.utils.ResponseToContentValues;

public class WorkerIntentService extends IntentService {
    private static final String ACTION_FOO = "im.kitillt.yandexmoneyclient.action.FOO";
    private static final String ACTION_BAZ = "im.kitillt.yandexmoneyclient.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "im.kitillt.yandexmoneyclient.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "im.kitillt.yandexmoneyclient.extra.PARAM2";

    public static void downloadNewHistory() {

    }

    public static void downloadAllHistory() {

    }

    private static void downloadHistory() {

    }

    private static void downloadHistoryFrom(DateTime dateTime) {

    }

    public static void downloadAccount(Context context, ResponseFailureCallback callback) throws Exception {
        YMCApplication.accountDownloadedNow = true;
        YMCApplication.auth2Session.enqueue(new AccountInfo.Request(), new ResponseReady<AccountInfo>() {
            @Override
            protected void failure(Exception exception) {
                YMCApplication.accountDownloadedNow = false;
                callback.call(exception);
            }

            @Override
            protected void response(AccountInfo response) {
                //TODO: rewrite it better way
                new AccountSelection().accountnumberNot(response.account).delete(context.getContentResolver());
                ResponseToContentValues.account(response).insert(context.getContentResolver());
                YMCApplication.accountDownloadedNow = false;
            }
        });
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, WorkerIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, WorkerIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public WorkerIntentService() {
        super("WorkerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
