package com.example.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ai.face.R;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientActivity extends AppCompatActivity {
    private EditText ipAddressInput;
    private Button connectButton;
    private TextView clientStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ipAddressInput = findViewById(R.id.ip_address_input);
        connectButton = findViewById(R.id.connect_button);
        clientStatus = findViewById(R.id.client_status);

        connectButton.setOnClickListener(v -> {
            final String ipAddress = ipAddressInput.getText().toString().trim();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket(ipAddress, 8080);
                        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                        out.println("Hello from device B!");

                        runOnUiThread(() -> clientStatus.setText("消息已发送到服务器"));

                        out.close();
                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }
}
