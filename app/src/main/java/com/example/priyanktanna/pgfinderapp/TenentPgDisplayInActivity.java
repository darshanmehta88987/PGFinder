package com.example.priyanktanna.pgfinderapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class TenentPgDisplayInActivity extends AppCompatActivity {

    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    TextView tvpgName,tvpgAdd,tvsingleRent,tvmultiRent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenent_pg_display_in);

        tvpgName = (TextView)findViewById(R.id.tv_pgname);
        tvpgAdd = (TextView)findViewById(R.id.tv_address);
        tvsingleRent = (TextView)findViewById(R.id.single_rent);
        tvmultiRent = (TextView)findViewById(R.id.Multi_rent);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        Intent intent = getIntent();
        ArrayList<String> facilityList;
        ArrayList<String> url;
        String pgName = intent.getStringExtra("pgName");
        String pgAdd = intent.getStringExtra("pgAdd");
        String pgFor = intent.getStringExtra("pgFor");
        String singleSpin = intent.getStringExtra("singleSpin");
        String multiSpin = intent.getStringExtra("multiSpin");
        String avgPerSpi = intent.getStringExtra("avgPerSpi");
        String multiRent = intent.getStringExtra("multiRent");
        String singleRent = intent.getStringExtra("singleRent");
        facilityList = intent.getStringArrayListExtra("facilityList");
        url = intent.getStringArrayListExtra("url");
        String id = intent.getStringExtra("tid");
        for (int i=0;i<url.size();i++) {
            Log.e("LIST_URL",url.get(i).toString());
        }
        myCustomPagerAdapter = new MyCustomPagerAdapter(TenentPgDisplayInActivity.this, url);
        viewPager.setAdapter(myCustomPagerAdapter);

        tvpgName.setText(pgName);
        tvpgAdd.setText(pgAdd);
        tvsingleRent.setText(singleRent);
        tvmultiRent.setText(multiRent);



    }
}
