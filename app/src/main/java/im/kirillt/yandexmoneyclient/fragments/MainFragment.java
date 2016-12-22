package im.kirillt.yandexmoneyclient.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import im.kirillt.yandexmoneyclient.MainActivity;
//import im.kirillt.yandexmoneyclient.PaymentActivity_old;
//import im.kirillt.yandexmoneyclient.PaymentActivity;
import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns;

import static im.kirillt.yandexmoneyclient.utils.Converters.bigDecimalToAmountString;


public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private LinearLayout accountInfoContainer;
    private FloatingActionButton fab;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MainFragment", "onCreate()");
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("MainFragment", "onCreateView()");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("MainFragment", "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        accountInfoContainer = ((MainActivity)getActivity()).getToolBarInnerView();
        accountInfoContainer.setVisibility(View.VISIBLE);
        fab = ((MainActivity)getActivity()).getFab();
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(v -> {
//            PaymentActivity.startActivity(getContext());
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (accountInfoContainer != null) {
            accountInfoContainer.setVisibility(View.GONE);
        }
        if (fab != null) {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                AccountColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setAccountData(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setAccountData(Cursor data) {
        if (data == null) {
            return;
        }
        data.moveToNext();
        if (data.getCount() == 0) {
            return;
        }
        TextView login = (TextView) accountInfoContainer.findViewById(R.id.account_info_user_name);
        login.setText(data.getString(data.getColumnIndex(AccountColumns.ACCOUNTUSERNAME)));
        TextView accountNumber = (TextView) accountInfoContainer.findViewById(R.id.account_info_account_number);
        accountNumber.setText(data.getString(data.getColumnIndex(AccountColumns.ACCOUNTNUMBER)));
        TextView balance = (TextView) accountInfoContainer.findViewById(R.id.account_info_balance);
        balance.setText(bigDecimalToAmountString(new BigDecimal(data.getString(data.getColumnIndex(AccountColumns.BALANCE)))));
        TextView reserved = (TextView) accountInfoContainer.findViewById(R.id.account_info_reserved);
        reserved.setText(bigDecimalToAmountString(new BigDecimal(data.getString(data.getColumnIndex(AccountColumns.BALANCEHOLD)))));
    }

}
