package nkosi.roger.manutdcom.view;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.adapter.HeadlineAdapter;
import nkosi.roger.manutdcom.controller.APIController;
import nkosi.roger.manutdcom.model.HeadlinesModel;


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
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiController = new APIController(this);
        apiController.fetchHeadlines(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.headlines);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
                apiController.refetchHeadlines(getContext());
            }
        });

        adapter = new HeadlineAdapter(list, getContext());
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
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchFailed() {

    }


}
