package ro.pub.cs.systems.eim.practicaltest02v8;

import static java.lang.Integer.parseInt;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientTask {

    private final Handler mainHandler;
    private final ExecutorService exec;
    private TextView msg;


    public ClientTask(TextView textView) {
        this.msg = textView;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.exec = Executors.newSingleThreadExecutor();
    }

    protected String doInBackground(String addr, String port, String url) {

        if (addr.isEmpty() || port.isEmpty()) return "";

        msg.setText("");

        exec.execute( () -> {
            Socket socket = null;
            try {
                socket = new Socket(addr, parseInt(port));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

                printWriter.println(url);

                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    String line = currentLine;
                    mainHandler.post(() -> {
                        msg.append(line + "\n");
                    });
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        return "";
    }
}
