package nkosi.roger.manutdcom.view;

import android.content.Context;
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
import nkosi.roger.manutdcom.controller.APIController;
import nkosi.roger.manutdcom.model.BlogModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchBlog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchBlog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchBlog extends Fragment implements APIController.BlogCallBackListener {
    private APIController controller;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<BlogModel> list = new ArrayList<>();
    private BlogAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controller = new APIController(this);
        this.controller.fetchBlog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_blog, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.blog);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        adapter = new BlogAdapter(list);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
                controller.refetchBlog(getContext());
            }
        });

        return view;
    }


    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(BlogModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<BlogModel> blogModels) {

    }

    @Override
    public void onFetchComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchFailed() {

    }
}
