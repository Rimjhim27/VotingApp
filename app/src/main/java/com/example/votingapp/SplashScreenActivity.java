package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;


import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginOrRegister.class)
                .withSplashTimeOut(1500)
                .withBackgroundResource(R.drawable.bg_ui)
                .withAfterLogoText("VOTING APP")
                .withLogo(R.mipmap.ic_launcher_round);
        config.getAfterLogoTextView().setTextColor(ContextCompat.getColor(this,R.color.imperialRed));
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
