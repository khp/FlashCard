package com.khp.flashcard.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(Splash.this, MainList.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
