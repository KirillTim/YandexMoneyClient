package im.kirillt.yandexmoneyclient.fragments.auth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import im.kirillt.yandexmoneyclient.R;

public class ErrorFragment extends Fragment {
    private static final String ARG_MESSAGE = "message";
    private String errorMessage;

    public static ErrorFragment newInstance(String message) {
        ErrorFragment fragment = new ErrorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    public ErrorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            errorMessage = getArguments().getString(ARG_MESSAGE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_error, container, false);
        TextView textView = (TextView)root.findViewById(R.id.fragment_error_msg);
        textView.setText(errorMessage);
        return root;
    }



}
