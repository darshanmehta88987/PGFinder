package com.example.priyanktanna.pgfinderapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public MyPagerAdapter(FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);
        this.tabCount=tabCount;

    }


    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                Fragment1 fragment1= new Fragment1();
                return fragment1;

            case 1:
                Fragment2 fragment2= new Fragment2();
                return fragment2;

            case 2:
                Fragment3 fragment3=new Fragment3();
                return fragment3;

              default:
                  return null;

        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
