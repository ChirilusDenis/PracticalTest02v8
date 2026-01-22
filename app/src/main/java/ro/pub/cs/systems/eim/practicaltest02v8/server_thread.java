package ro.pub.cs.systems.eim.practicaltest02v8;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class server_thread extends Thread{

    private boolean isRunning;
    private ServerSocket serverSocket;
    private int port;

    public void startServer() {
        isRunning = true;
        start();
        Log.v("THIS", "startServer() method was invoked");
    }

    public server_thread(int port) {
        this.port = port;
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ioException) {
            Log.e("THIS", "An exception has occurred: " + ioException.getMessage());
        }
        Log.v("THIS", "stopServer() method was invoked");
    }

    private String reques(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = null;

        request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {

                return response.body().string();
            } else return "";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"));
            while (isRunning) {
                Socket socket = serverSocket.accept();
//                Log.v("THIS", "accept()-ed: " + socket.getInetAddress());
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String url = bufferedReader.readLine();
                if (url.contains("bad")) {
                    printWriter.println("URL blocked by firewall.\n");
                } else {
                    printWriter.println(reques(url));
                }

                socket.close();
            }
        } catch (IOException ioException) {
            Log.e("THIS", "An exception has occurred: " + ioException.getMessage());
        }
    }
}
