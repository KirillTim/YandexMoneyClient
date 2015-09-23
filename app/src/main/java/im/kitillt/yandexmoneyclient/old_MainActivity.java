//package im.kitillt.yandexmoneyclient;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.util.Pair;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.yandex.money.api.methods.OperationHistory;
//import com.yandex.money.api.model.Scope;
//import com.yandex.money.api.net.DefaultApiClient;
//import com.yandex.money.api.net.OAuth2Authorization;
//import com.yandex.money.api.net.OAuth2Session;
//
//import java.io.IOException;
//import java.util.HashSet;
//
//import im.kitillt.yandexmoneyclient.utils.ResponseReady;
//import im.kitillt.yandexmoneyclient.utils.Threads;
//
//public class old_MainActivity extends AppCompatActivity {
//
//
//    public Context context;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        context = this;
//        Button button = (Button) findViewById(R.id.auth_button);
//        button.setOnClickListener(v -> {
//            Pair<String, byte[]> data = wtf();
//            Intent intent = new Intent(context, AuthActivity.class);
//            intent.putExtra(AuthActivity.KEY_URL, data.first);
//            intent.putExtra(AuthActivity.KEY_POST_DATA, data.second);
//            startActivityForResult(intent, 1);
//        });
//        Log.i("Main thread id: ", Thread.currentThread().getId()+"");
//        Button loadHist = (Button) findViewById(R.id.load_history_button);
//        loadHist.setOnClickListener(v -> {
//            String token = getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(YMCApplication.PREF_AUTH_TOKEN, "");
//            if (TextUtils.isEmpty(token)) {
//                Toast.makeText(context, "Code not found", Toast.LENGTH_SHORT).show();
//            } else {
//                OAuth2Session session = new OAuth2Session(YMCApplication.apiClient);
//                session.setAccessToken(token);
//                HashSet<OperationHistory.FilterType> types = new HashSet<>();
//                types.add(OperationHistory.FilterType.DEPOSITION);
//                types.add(OperationHistory.FilterType.PAYMENT);
//                final OperationHistory.Request request = new OperationHistory.Request.Builder().setTypes(types).setDetails(true).createRequest();
//                try {
//                    session.enqueue(request, new ResponseReady<OperationHistory>() {
//                        @Override
//                        protected void failure(Exception exception) {
//                            Toast.makeText(context, "Fail: " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        protected void response(OperationHistory response) {
//                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                            Log.i("response thread id: ", Thread.currentThread().getId()+"");
//                            Log.i("response", response.toString());
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            for (int i = 0; i < 10; i++) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.i("sleep: ", i+"");
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data == null) {
//            return;
//        }
//        boolean success = data.getBooleanExtra(AuthActivity.RESULT_SUCCESS, false);
//        if (success) {
//            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Fail: " + data.getStringExtra(AuthActivity.RESULT_ERROR_MSG), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public Pair<String, byte[]> wtf() {
//        OAuth2Authorization auth2Authorization = new OAuth2Authorization(new DefaultApiClient(YMCApplication.APP_ID, true, "Android"));
//        OAuth2Authorization.Params params = auth2Authorization.getAuthorizeParams()
//                .addScope(Scope.ACCOUNT_INFO)
//                .addScope(Scope.INCOMING_TRANSFERS)
//                .addScope(Scope.OPERATION_DETAILS)
//                .addScope(Scope.OPERATION_HISTORY)
//                .addScope(Scope.PAYMENT_P2P)
//                .setRedirectUri(YMCApplication.REDIRECT_URI);
//        return new Pair<>(auth2Authorization.getAuthorizeUrl(), params.build());
//    }
//}
