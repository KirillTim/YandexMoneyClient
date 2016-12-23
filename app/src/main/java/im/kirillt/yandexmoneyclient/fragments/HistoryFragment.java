package im.kirillt.yandexmoneyclient.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.HistoryCursorAdapter;
import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent;
import im.kirillt.yandexmoneyclient.events.download.SuccessDownloadEvent;
import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns;


public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private HistoryCursorAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private View rootView;
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
        adapter = new HistoryCursorAdapter(getContext(), null, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_history, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView)rootView.findViewById(R.id.history_list);
        listView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (YMCApplication.isDownloading()) {
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            downloadData();
        });
        swipeRefreshLayout.setRefreshing(YMCApplication.isDownloading());}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                OperationColumns.CONTENT_URI, null, null, null, OperationColumns.DATETIME+" DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void downloadData() {
        EventBus.getDefault().post(new DownloadAllEvent(getActivity()));
    }

    public void onEventMainThread(SuccessDownloadEvent event) {
        if (!YMCApplication.isDownloading()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

//    public void onEventAsync(IncomingTransferProcessEvent event) {
//        event.process();
//    }

}
