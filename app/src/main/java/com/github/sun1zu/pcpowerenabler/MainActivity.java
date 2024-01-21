package com.github.sun1zu.pcpowerenabler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener{

    private Button TurnOnPc;
    private ImageButton ToggleVolume;
    private MediaPlayer mediaPlayer;
    private GifImageView Toothless;
    private TextView status;
    private boolean VolumeEnabled = false;
    private Button inputIpMac;

    private String ip; private int port; private String mac;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO: SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this); //serializing ip, mac, port

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TurnOnPc = (Button) findViewById(R.id.button2);
        ToggleVolume = (ImageButton) findViewById(R.id.floatingActionButton);
        inputIpMac = (Button) findViewById(R.id.inputIpMac);
        mediaPlayer = MediaPlayer.create(this, R.raw.dance);
        Toothless = (GifImageView) findViewById(R.id.my_gif);
        status = (TextView) findViewById(R.id.status);


        TurnOnPc.setOnClickListener(view -> {
            SocketThread socketThread = new SocketThread();
            socketThread.execute(ip, "" + port, mac);
            status.setText(socketThread.getTaskStatus());
        });

        inputIpMac.setOnClickListener(view -> {
            openDialog();
        });



        ToggleVolume.setOnClickListener(view -> {
            VolumeEnabled = !VolumeEnabled;
            if(!VolumeEnabled){
                ToggleVolume.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                Toothless.setVisibility(View.INVISIBLE);
                mediaPlayer.pause();
            }
            else {
                ToggleVolume.setImageResource(R.drawable.ic_baseline_pause_24);
                Toothless.setVisibility(View.VISIBLE);
                mediaPlayer.start();
            }
        });
    }

    public void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "inputDialog");
    }

    @Override
    public void applyTexts(String ip, int port, String mac) {
        this.ip = ip;
        this.port = port;
        this.mac = mac;
    }

    @Override
    public void getErrorCode(String code){
        status.setText(code);
    }
}