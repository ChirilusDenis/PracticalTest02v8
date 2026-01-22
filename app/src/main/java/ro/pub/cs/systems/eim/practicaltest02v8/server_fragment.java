package ro.pub.cs.systems.eim.practicaltest02v8;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class server_fragment extends Fragment {

    Button server_start;
    EditText server_port;
    server_thread serverThread = null;

    public server_fragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server_fragment, container, false);
        server_start = view.findViewById(R.id.client_request);
        server_port = view.findViewById(R.id.client_port);

        server_start.setOnClickListener(v -> {
            if (serverThread == null) {
                serverThread = new server_thread(parseInt(server_port.getText().toString()));
                serverThread.startServer();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        serverThread.stopServer();
        super.onDestroyView();
    }
}