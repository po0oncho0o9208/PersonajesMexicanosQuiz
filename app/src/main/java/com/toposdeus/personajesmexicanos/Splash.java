package com.toposdeus.personajesmexicanos;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class Splash extends Activity {


    private static final long tiempoespera = 3000;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
        image = (ImageView) findViewById(R.id.imagensplash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // codigo a ejecutar
                Intent mainIntent = new Intent().setClass(
                        Splash.this, PantallaPrincipal.class);
                startActivity(mainIntent);
                finish();
            }
        };
        // Simulate a long loading process on application startup.
        Animation transparente = AnimationUtils.loadAnimation(this, R.anim.transparencia);
        transparente.reset();
        image.startAnimation(transparente);
        Timer timer = new Timer();
        timer.schedule(task, tiempoespera);
    }
}