package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {


    int splashtime = 1000;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);
        final String strKey = sharedPreferences.getString("USER_KEY","");
        final String strRole = sharedPreferences.getString("USER_ROLE","");




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Log.e("SplashActivity","key"+strKey);

                if (strKey.isEmpty() && strKey.equals("")){

                    Intent i = new Intent(SplashActivity.this,login_Activity.class);
                    startActivity(i);
                    finish();


                }else  if (!strKey.isEmpty() && !strKey.equals("")){

                    if (strRole.equals("User")){


                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    if (strRole.equals("Tenent")) {

                        Intent i1 = new Intent(SplashActivity.this, TabActivity.class);
                        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        finish();
                    }

                }else {

                    Intent i = new Intent(SplashActivity.this,WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }





            }
        },splashtime);

    }
}
