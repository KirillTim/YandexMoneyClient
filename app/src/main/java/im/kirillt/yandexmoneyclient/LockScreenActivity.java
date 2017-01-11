package im.kirillt.yandexmoneyclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import im.kirillt.yandexmoneyclient.model.util.TextWatcherAdapter;

public class LockScreenActivity extends AppCompatActivity {

    private static final String CODE = "code";

    private ImageView submit;
    private TextView reset;
    private EditText passwordEditText;
    private TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        passwordEditText = (EditText)findViewById(R.id.lock_screen_edit_text);
        error = (TextView)findViewById(R.id.lock_screen_error);
        error.setVisibility(View.INVISIBLE);
        submit = (ImageView)findViewById(R.id.lock_screen_submit_button);
        submit.setOnClickListener(v -> {
            String code = passwordEditText.getText().toString();
            if (code.length() < 4) {
                Toast.makeText(LockScreenActivity.this, getString(R.string.at_least_4), Toast.LENGTH_SHORT).show();
                return;
            }
            if (((YMCApplication)getApplication()).getInstance().validateCode(code)) {
                String tokenStr = getSharedPreferences(YMCApplication.Companion.getPREFERENCES_STORAGE(), 0).getString(YMCApplication.Companion.getPREF_AUTH_TOKEN(), "");
                String token = new String(Encryption.encryptToken(tokenStr, code).toByteArray());
                YMCApplication.Companion.setToken(token);
                setResult(RESULT_OK);
                YMCApplication.Companion.setAskLock(false);
                finish();
            } else {
                passwordEditText.setText("");
                error.setVisibility(View.VISIBLE);
            }
        });
        reset = (TextView)findViewById(R.id.lock_screen_forget_button);
        reset.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LockScreenActivity.this);
            builder.setPositiveButton(R.string.cancel, (dialog, id) -> {
                dialog.dismiss();
            });
            builder.setNegativeButton(R.string.logout, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    YMCApplication.Companion.deleteToken(LockScreenActivity.this);
                    dialog.cancel();
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
            builder.setTitle(getString(R.string.logout_from_acc));
            builder.setMessage(getString(R.string.forget_lock_msg_line1));
            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //YOU SHELL NOT PASS!!!!!
        setResult(RESULT_CANCELED);
        finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CODE, passwordEditText.getText().toString());
    }
}

