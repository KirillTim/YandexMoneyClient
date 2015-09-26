package im.kirillt.yandexmoneyclient.fragments.auth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        error = (TextView)root.findViewById(R.id.fragment_create_lock_code_second_wrong);
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
            } else {
                if (code.equals(firstCode)) {
                    ((AuthActivity)getActivity()).getLockCode(code);
                } else {
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
        reset = (TextView)root.findViewById(R.id.fragment_create_lock_code_reset_button);
        reset.setOnClickListener(v -> {
            firstCode = "";
            passwordEditText.setText("");
        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
