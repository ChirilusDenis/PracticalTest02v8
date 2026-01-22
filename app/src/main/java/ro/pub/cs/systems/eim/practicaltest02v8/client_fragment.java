package ro.pub.cs.systems.eim.practicaltest02v8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class client_fragment extends Fragment {

    public client_fragment() {
        // Required empty public constructor
    }

    Button request;
    EditText addr;
    EditText port;
    TextView msg;
    EditText url_edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_fragment, container, false);
        addr = view.findViewById(R.id.client_addr);
        port = view.findViewById(R.id.client_port);
        msg = view.findViewById(R.id.client_response);
        request = view.findViewById(R.id.client_request);
        url_edit = view.findViewById(R.id.client_url);


        request.setOnClickListener(v -> {
            ClientTask clientTask = new ClientTask(msg);
            clientTask.doInBackground(addr.getText().toString(), port.getText().toString(), url_edit.getText().toString());
        });

        return view;
    }

}