package nkosi.roger.manutdcom.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.controller.APIController;


public class AppIntorFragmentOne extends Fragment {

    private APIController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new APIController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_intor_fragment_one, container, false);

        setView(view);

        return view;
    }

    private void setView(View view) {
        TextView textView = (TextView)view.findViewById(R.id.content);
        this.controller.getHistory(getContext(), textView);
    }

}
