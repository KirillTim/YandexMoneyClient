package im.kirillt.yandexmoneyclient.fragments.auth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import im.kirillt.yandexmoneyclient.AuthActivity;
import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.utils.MyTextWatcher;

public class CreateLockCodeFragment extends Fragment {

    private ImageView submit;
    private TextView reset;
    private EditText passwordEditText;
    private TextView error;

    private String firstCode = "";

    public static CreateLockCodeFragment newInstance() {
        return new CreateLockCodeFragment();
    }

    public CreateLockCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_lock_code, container, false);
        passwordEditText = (EditText)root.findViewById(R.id.fragment_create_lock_code_edit_text);
        passwordEditText.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                error.setVisibility(View.INVISIBLE);
            }
        });
        error = (TextView)root.findViewById(R.id.fragment_create_lock_code_second_msg);
        error.setVisibility(View.INVISIBLE);
        submit = (ImageView)root.findViewById(R.id.fragment_create_lock_code_submit_button);
        submit.setOnClickListener(v -> {
            String code = passwordEditText.getText().toString();
            if (code.length() < 4) {
                return;
            }
            if (firstCode.equals("")) {
                firstCode = code;
                passwordEditText.setText("");
                error.setText(getString(R.string.enter_code_second_time));
                error.setTextColor(getResources().getColor(R.color.text_color_dark));
                error.setVisibility(View.VISIBLE);
            } else {
                if (code.equals(firstCode)) {
                    ((AuthActivity)getActivity()).getLockCode(code);
                } else {
                    error.setText(getString(R.string.password_doesnt_match));
                    error.setTextColor(getResources().getColor(R.color.red_dark));
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
        reset = (TextView)root.findViewById(R.id.fragment_create_lock_code_reset_button);
        reset.setOnClickListener(v -> {
            firstCode = "";
            passwordEditText.setText("");
            error.setVisibility(View.INVISIBLE);
        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            firstCode = savedInstanceState.getString("first");
            String nowEdit = savedInstanceState.getString("now");
            if (nowEdit != null) {
                passwordEditText.setTag(nowEdit);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("first", firstCode);
        outState.putString("now", passwordEditText.getText().toString());
    }


}
