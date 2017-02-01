package nkosi.roger.manutdcom.view;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.adapter.EventsAdapter;
import nkosi.roger.manutdcom.controller.APIController;
import nkosi.roger.manutdcom.model.LiveEventsModel;
import nkosi.roger.manutdcom.utils.aUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveMatch extends Fragment implements APIController.LiveEventCallBackListener {

    private APIController apiController;
    private RecyclerView recyclerView;
    private List<LiveEventsModel> list = new ArrayList<>();
    private EventsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public LiveMatch() {
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
        apiController = new APIController(this);
        apiController.fetchEvents(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_match, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.live_events);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        adapter = new EventsAdapter(list, getContext());

        setView(view);

        recyclerView.setAdapter(adapter);

        return view;

    }

    public void setView(View view){
        final Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arcon_regular.otf");
        final TextView duration = (TextView)view.findViewById(R.id.duration);
        final TextView hometeam = (TextView)view.findViewById(R.id.home_team);
        final TextView awayteam = (TextView)view.findViewById(R.id.away_team);
        final TextView score = (TextView)view.findViewById(R.id.score);
        final TextView matchdate = (TextView)view.findViewById(R.id.matchdate);
        final TextView matchlocation = (TextView)view.findViewById(R.id.matchlocation);
        final TextView competition = (TextView)view.findViewById(R.id.competition);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.live_score_view);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.live_swipe);

        duration.setTypeface(typeface, Typeface.BOLD);
        hometeam.setTypeface(typeface, Typeface.BOLD);
        awayteam.setTypeface(typeface, Typeface.BOLD);
        score.setTypeface(typeface, Typeface.BOLD);
        matchdate.setTypeface(typeface);
        matchlocation.setTypeface(typeface);
        competition.setTypeface(typeface);

//
//        while(score.getText().length() < 1){
//            notShowing.setText("dsufhulisdhflisduh");
////            Log.e("LiveMatch", "score empty");
//        }

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
                apiController.refetchEvents(getContext());
            }
        });

        apiController.fetchLiveMatch(duration, hometeam, awayteam, score,
                matchdate, matchlocation, competition);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiController.refetchLiveMatch(duration, hometeam, awayteam, score,
                        matchdate, matchlocation, competition, getContext());
                Log.e("onclick", "lets go");
            }
        });
    }



    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(LiveEventsModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<LiveEventsModel> modelList) {

    }

    @Override
    public void onFetchComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchFailed() {

    }

}
