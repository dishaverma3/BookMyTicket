package com.example.bookmyticket.ui.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.bookmyticket.R;
import com.example.bookmyticket.ui.MainActivity;
import com.example.bookmyticket.ui.MovieList.MovieListActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);
        logo.setAnimation(animation);

        final Intent i = new Intent(SplashActivity.this, MovieListActivity.class);
        Thread timer = new Thread()
        {
            public void run()
            {
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();
    }
}
