package nkosi.roger.manutdcom.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.controller.APIController;
import nkosi.roger.manutdcom.utils.aUtils;

/**
 * A simple {@link Fragment} subclass.
 */

public class Contact extends Fragment implements View.OnClickListener{

    private EditText name, mail, comment;
    private Button send;
    private APIController apiController;


    public Contact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiController = new APIController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        name = (EditText)view.findViewById(R.id.name);
        comment = (EditText)view.findViewById(R.id.comment);
        mail = (EditText)view.findViewById(R.id.email);
        send = (Button)view.findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    private void sendComment(){

        final String name = this.name.getText().toString();
        final String comment = this.comment.getText().toString();
        final String mail = this.mail.getText().toString();

        if (name.isEmpty()){
            aUtils.showAlertDialog(getContext(), "Contact us", "Please fill in your name");
            return;
        }

        if (comment.isEmpty()){
            aUtils.showAlertDialog(getContext(), "Contact us", "Please fill in your comment");
            return;
        }

        if (mail.isEmpty()){
            aUtils.showAlertDialog(getContext(), "Contact us", "Please fill in your e-mail address");
            return;
        }else {
            if (aUtils.isValidEmailAddress(mail)) {
                // send data
                apiController.sendContact(getContext(), name, mail, comment);
            }else {
                aUtils.showAlertDialog(getContext(), "Contact us", "E-mail address is not valid");
            }
        }

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.send:
                sendComment();
                break;
            default:
                return;
        }
    }
}
