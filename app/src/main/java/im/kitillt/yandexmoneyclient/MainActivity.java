package im.kitillt.yandexmoneyclient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yandex.money.api.model.Scope;
import com.yandex.money.api.net.ApiClient;
import com.yandex.money.api.net.DefaultApiClient;
import com.yandex.money.api.net.OAuth2Authorization;

public class MainActivity extends AppCompatActivity {

    public static String APP_ID = "DDD264D223C195815CB984B24B74220E9A551C81F9163AAD41A55EDA98C03E98";
    public static String REDIRECT_URI = "client://authresult";
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Button button = (Button)findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<String, byte[]> data = wtf();
                Intent intent = new Intent(context, AuthActivity.class);
                intent.putExtra(AuthActivity.KEY_URL, data.first);
                intent.putExtra(AuthActivity.KEY_POST_DATA, data.second);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Pair<String, byte[]> wtf() {
        OAuth2Authorization auth2Authorization = new OAuth2Authorization(new DefaultApiMobileClient(APP_ID, true, "Android"));
        OAuth2Authorization.Params params = auth2Authorization.getAuthorizeParams()
                    .addScope(Scope.ACCOUNT_INFO)
                    .addScope(Scope.INCOMING_TRANSFERS)
                    .addScope(Scope.OPERATION_DETAILS)
                    .addScope(Scope.OPERATION_HISTORY)
                    .addScope(Scope.PAYMENT_P2P)
                .setRedirectUri(REDIRECT_URI);
        return new Pair<>(auth2Authorization.getAuthorizeUrl(),params.build());
    }
}
