package nkosi.roger.manutdcom.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.controller.APIController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThree extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_fragment_three, container, false);
        setView(view);
        return view;
    }

    private void setView(View view) {
        TextView textView = (TextView)view.findViewById(R.id.content);
        this.controller.getHistory(getContext(), textView);
    }



}
