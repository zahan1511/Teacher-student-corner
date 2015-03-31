package com.example.zahan.tsc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


public class Splash extends Activity {
    Intent login;
    ImageView image;
    Animation down, up, animation, still;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        //initialize elements
        image = (ImageView) findViewById(R.id.image);
        login = new Intent("com.example.zahan.tsc.Login");


        //define animation for freeze position
        still = new TranslateAnimation(0, 0, 0, 0);
        still.setDuration(1500);
        still.setFillAfter(true);
        //define first part of animation
        down = new TranslateAnimation(0, 0, 0, 50);
        down.setDuration(700);
        down.setFillAfter(true);
        //define second part of animation
        animation = new TranslateAnimation(0, 0, 50, -240);
        animation.setDuration(500);
        animation.setFillAfter(true);
        //define last part of animation
        up = new TranslateAnimation(0, 0, -240, -190);
        up.setDuration(300);
        up.setFillAfter(true);

        //adding listener to still animation
        still.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation still) {

            }

            @Override
            public void onAnimationEnd(Animation still) {
                //start second part animation
                image.startAnimation(down);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //adding listener to first animation
        down.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation down) {

            }

            @Override
            public void onAnimationEnd(Animation down) {
                //start second part animation
                image.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        //adding listener to second part of animation
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //start last part of animation
                image.startAnimation(up);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        //adding listener to last part of animation
        up.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation up) {

            }

            @Override
            public void onAnimationEnd(Animation up) {
                //sleep for .5 sec and go to login activity
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(login);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        //start still animation
        image.startAnimation(still);

    }



    @Override
    protected void onPause() {
        super.onPause();
        //finishing activity when goes to pause
        finish();
    }
}