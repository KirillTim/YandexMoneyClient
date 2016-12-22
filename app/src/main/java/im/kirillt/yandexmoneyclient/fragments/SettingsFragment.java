//package im.kirillt.yandexmoneyclient.fragments;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.yandex.money.api.methods.Token;
//
//import java.io.IOException;
//
//import im.kirillt.yandexmoneyclient.R;
//import im.kirillt.yandexmoneyclient.YMCApplication;
//import im.kirillt.yandexmoneyclient.provider.account.AccountSelection;
//import im.kirillt.yandexmoneyclient.provider.operation.OperationSelection;
////import im.kirillt.yandexmoneyclient.utils.ResponseReady;
//
//public class SettingsFragment extends Fragment {
//
//    public static SettingsFragment newInstance() {
//        return new SettingsFragment();
//    }
//
//    public SettingsFragment() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        Log.i("SettingsFragment", "onCreate()");
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        Log.i("SettingsFragment", "onCreate()");
//        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ((Button) getView().findViewById(R.id.fragment_settings_clear_cache_button)).setOnClickListener(v -> {
//            int op = new OperationSelection().operationidNot("").delete(getActivity().getContentResolver());
//            int acc = new AccountSelection().accountnumberNot("").delete(getActivity().getContentResolver());
//            Log.i("Settings Fragment", op + " operations deleted");
//            Log.i("Settings Fragment", acc + " accounts deleted");
//        });
//        getView().findViewById(R.id.fragment_settings_logout_button).setOnClickListener(v -> {
//            String tokenStr = getActivity().getSharedPreferences(YMCApplication.PREFERENCES_STORAGE, 0).getString(YMCApplication.PREF_AUTH_TOKEN, "");
//            Token token = new Token(tokenStr, null);
//            try {
//                YMCApplication.auth2Session.enqueue(new Token.Revoke(), new ResponseReady<Object>() {
//                    @Override
//                    protected void failure(Exception exception) {
//                        Log.i("settings", "fail");
//                        YMCApplication.deleteToken(getActivity());
//                        getActivity().finish();
//                    }
//
//                    @Override
//                    protected void response(Object response) {
//                        Log.i("settings", "ok");
//                        YMCApplication.deleteToken(getActivity());
//                        getActivity().finish();
//                    }
//                });
//            } catch (Exception e) {
//                YMCApplication.deleteToken(getActivity());
//                e.printStackTrace();
//            }
//        });
//    }
//
//}
