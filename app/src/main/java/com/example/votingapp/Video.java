package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String videourl = getIntent().getStringExtra("video_url");
        Toast.makeText(getApplicationContext(),videourl,Toast.LENGTH_SHORT).show();
    }
}
