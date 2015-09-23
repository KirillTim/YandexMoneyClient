package im.kirillt.yandexmoneyclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.R;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.download.DownloadAllEvent;

/**
 * Wrapper {@link Fragment} handled update events and store information(another Fragment) inside
 * Use the {@link UpdatableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatableFragment extends Fragment {
    private static final String ARG_FRAGMENT_NAME = "fragmentName";
    public static final int MAIN_FRAGMENT = 0;
    public static final int HISTORY_FRAGMENT = 1;

    private Fragment innerFragment;
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Factory method to create a new instance of
     * fragment with inner Fragment.
     *
     * @param innerFragmentName name of the fragment to place inside
     * @return A new instance of fragment UpdatableFragment.
     */
    public static UpdatableFragment newInstance(int innerFragmentName) {
        UpdatableFragment fragment = new UpdatableFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_NAME, innerFragmentName);
        fragment.setArguments(args);
        return fragment;
    }

    public UpdatableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int fragmentName = getArguments().getInt(ARG_FRAGMENT_NAME);
            switch (fragmentName) {
                case MAIN_FRAGMENT:
                    innerFragment = MainFragment.newInstance();
                    break;
                case HISTORY_FRAGMENT:
                    innerFragment = HistoryFragment.newInstance();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown inner Fragment");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_updatable, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (YMCApplication.isDownloading()) {
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
            downloadData();
        });
        swipeRefreshLayout.setRefreshing(YMCApplication.isDownloading());
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getChildFragmentManager().beginTransaction().add(R.id.inner_fragment, innerFragment).commit();

    }

    private void downloadData() {
        EventBus.getDefault().post(new DownloadAllEvent(getActivity()));
    }
}
