package nkosi.roger.manutdcom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements APIController.HeadlinesCallBackListener {

    private APIController apiController;
    private RecyclerView recyclerView;
    private List<HeadlinesModel> list = new ArrayList<>();
    private HeadlineAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiController = new APIController(this);
        apiController.fetchHeadlines();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.headlines);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        adapter = new HeadlineAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(HeadlinesModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<HeadlinesModel> modelList) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onFetchFailed() {

    }

    private class HeadlineAdapter extends RecyclerView.Adapter<HeadlineAdapter.Holder>{

        public String TAG = HomeFragment.class.getSimpleName();
        private List<HeadlinesModel> postModels;

        public HeadlineAdapter(List<HeadlinesModel> postModels) {
            this.postModels = postModels;
        }

        public void populate(HeadlinesModel model){
            postModels.add(model);
            notifyDataSetChanged();
        }

        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
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
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_headlines,parent, false);
            return new Holder(row);
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p>
         * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(Holder holder, int position) {
            final HeadlinesModel model = this.postModels.get(position);
            holder.source.setText("Source : " + model.source);
            holder.details.setText(model.details.substring(0, 130)+"...");
            holder.headline.setText(model.headline);

            Picasso.with(holder.itemView.getContext()).load(Constants.BASE_URL + "images/" + model.img).into(holder.imageView);

            holder.parentView.setSelected(list.contains(position));

            holder.parentView.setOnClickListener(new View.OnClickListener() {

                String id = model.hId;

                @Override
                public void onClick(View v) {
                    Log.e(id, id);
                }
            });
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
           return postModels.size();
        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView headline, details, source;
            public ImageView imageView;
            public View parentView;

            public Holder(View itemView) {
                super(itemView);
                this.parentView = itemView;
                imageView = (ImageView)itemView.findViewById(R.id.headline_thumbnail);
                headline = (TextView)itemView.findViewById(R.id.headline);
                details = (TextView)itemView.findViewById(R.id.details);
                source = (TextView)itemView.findViewById(R.id.source);
            }
        }
    }
}
