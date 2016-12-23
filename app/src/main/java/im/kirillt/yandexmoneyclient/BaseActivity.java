package im.kirillt.yandexmoneyclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (YMCApplication.askLock) {
            //startActivityForResult(new Intent(this, LockScreenActivity.class), 1);
        } else if (YMCApplication.askAuth) {
            startActivityForResult(new Intent(this, AuthActivity.class), 2);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1 && requestCode != 2) {
            return;
        }
        if (resultCode != RESULT_OK) {
            //android.os.Process.killProcess(android.os.Process.myPid());
            finish();
        }
    }
}
