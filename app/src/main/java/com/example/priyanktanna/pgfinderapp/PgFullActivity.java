package com.example.priyanktanna.pgfinderapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PgFullActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_full);

        Intent intent = getIntent();
        ArrayList<String> url = intent.getStringArrayListExtra("url");

        Log.e("PGIMAGE","PG URL SIZE "+url.size());
        viewPager = (ViewPager)findViewById(R.id.viewpager_full);

        MyPgFullAdapter myPgFullAdapter = new MyPgFullAdapter(PgFullActivity.this,url);
        viewPager.setAdapter(myPgFullAdapter);

    }
}
