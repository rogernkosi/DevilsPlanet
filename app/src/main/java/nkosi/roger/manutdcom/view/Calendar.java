package nkosi.roger.manutdcom.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.adapter.BlogAdapter;
import nkosi.roger.manutdcom.adapter.CalendarAdapter;
import nkosi.roger.manutdcom.controller.APIController;
import nkosi.roger.manutdcom.model.CalendarModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar extends Fragment implements APIController.CalendarCallBackListener{

    private APIController controller;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<CalendarModel> list = new ArrayList<>();
    private CalendarAdapter adapter;

    public Calendar() {
        // Required empty public constructor
    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p/>
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>Any restored child fragments will be created before the base
     * <code>Fragment.onCreate</code> method returns.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controller = new APIController(this);
        this.controller.fetchCalendar(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.calendar);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        adapter = new CalendarAdapter(list);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
                controller.refetchCalendar(getContext());
            }
        });

        return view;

    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(CalendarModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<CalendarModel> modelList) {

    }

    @Override
    public void onFetchComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchFailed() {

    }
}
