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

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.Holder>{

        private List<LiveEventsModel> liveEventsModels;
        private Context context;

        public EventsAdapter(List<LiveEventsModel> liveEventsModels, Context context) {
            this.liveEventsModels = liveEventsModels;
            this.context = context;
        }

        public void populate(LiveEventsModel model){
            liveEventsModels.add(model);
            notifyDataSetChanged();
        }

        public void refresh() {
            liveEventsModels.clear();
            notifyDataSetChanged();
        }

        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p/>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p/>
         * The new ViewHolder will be used to display items of the adapter using
         * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_live_events,parent, false);
            return new Holder(row);
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p/>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p/>
         * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle effcient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(Holder holder, int position) {
            final LiveEventsModel model = this.liveEventsModels.get(position);
            holder.eventTime.setText(model.eventTime);
            holder.eventTitle.setText(model.eventTitle);
            holder.eventDescr.setText(model.eventDescription);

//            Picasso.with(holder.itemView.getContext()).load(Constants.BASE_URL + "images/" + model.eventIcon).into(holder.imageView);

            holder.view.setSelected(list.contains(position));

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aUtils.invokeShare(context, model.eventTitle, model.eventDescription);
                }
            });
        }

        /**
         * Returns the total number of items in the data set hold by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return liveEventsModels.size();
        }

        public class Holder extends RecyclerView.ViewHolder{

            public TextView eventTime, eventDescr, eventTitle;
            public ImageView imageView;
            private View view;
            Typeface typeface, title;

            public Holder(View itemView) {
                super(itemView);
                this.view = itemView;
                this.eventDescr = (TextView)this.view.findViewById(R.id.live_event_description);
                this.eventTime = (TextView)this.view.findViewById(R.id.live_event_time);
                this.eventTitle = (TextView)this.view.findViewById(R.id.live_event_title);
//                this.imageView= (ImageView)this.view.findViewById(R.id.live_event_icon);
                this.typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arcon_regular.otf");

                this.title = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arcon_regular.otf");

                this.eventTitle.setTypeface(this.title, Typeface.BOLD);

                this.eventDescr.setTypeface(typeface);
                this.eventTitle.setTextColor(getResources().getColor(R.color.primaryFontColor));
                this.eventDescr.setTextColor(getResources().getColor(R.color.secondaryFontColor));
            }

        }
    }
}
