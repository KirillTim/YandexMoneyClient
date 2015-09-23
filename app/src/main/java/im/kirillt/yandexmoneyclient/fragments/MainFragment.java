package im.kirillt.yandexmoneyclient.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private View rootView;
    private LinearLayout accountInfoContainer;

    private HistoryFragment historyFragment;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        historyFragment = HistoryFragment.newInstance();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        accountInfoContainer = (LinearLayout)rootView.findViewById(R.id.account_info_linear_layout);
        Button payButton = (Button) rootView.findViewById(R.id.pay_button);
        payButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Pay Button Pressed!", Toast.LENGTH_SHORT).show();
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getChildFragmentManager().beginTransaction().add(R.id.fragment_history, historyFragment).commit();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                AccountColumns.CONTENT_URI, null, null, null , null );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setAccountData(accountInfoContainer, data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setAccountData(View container, Cursor data) {
        TextView accountNumber = (TextView)container.findViewById(R.id.account_number);
        accountNumber.setText(data.getString(data.getColumnIndex(AccountColumns.ACCOUNTNUMBER)));
        TextView balance = (TextView)container.findViewById(R.id.balance);
        balance.setText(new BigDecimal(data.getString(data.getColumnIndex(AccountColumns.BALANCE))).toPlainString());
        TextView reserved = (TextView)container.findViewById(R.id.reserved);
        reserved.setText(new BigDecimal(data.getString(data.getColumnIndex(AccountColumns.BALANCEHOLD))).toPlainString());
    }

}
