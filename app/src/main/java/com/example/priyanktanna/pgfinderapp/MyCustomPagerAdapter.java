package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyCustomPagerAdapter  extends PagerAdapter {


    private final LayoutInflater inflater;
    Context context;
    ArrayList<String> url;

    public MyCustomPagerAdapter(Context context, ArrayList<String> url) {

    this.context = context;
    this.url = url;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return url.size();
    }
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.raw_pager, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.imageView);
        Glide.with(context)
                .load(url.get(position).toString())
                .into(myImage);
        view.addView(myImageLayout, 0);


    myImageLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,PgFullActivity.class);
            intent.putStringArrayListExtra("url",url);

            context.startActivity(intent);
        }
    });


        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
