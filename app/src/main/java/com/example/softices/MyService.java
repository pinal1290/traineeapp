package com.example.softices;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import java.net.URI;

public class MyService extends Service {
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setLooping(true);
        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Start Successfully.", Toast.LENGTH_SHORT).show();
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        Toast.makeText(this, "Service Stopped Successfully.", Toast.LENGTH_SHORT).show();
    }
}
