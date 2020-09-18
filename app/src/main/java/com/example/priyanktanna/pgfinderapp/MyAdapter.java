package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<AndroidModel>androidModelArrayList;

    public MyAdapter(Context context,ArrayList<AndroidModel>androidModelArrayList){

        this.context=context;
        this.androidModelArrayList=androidModelArrayList;
    }

    @Override
    public int getCount() {
        return androidModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return androidModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView= layoutInflater.inflate(R.layout.raw_visitorlistview,null);

        ImageView imgData=(ImageView)convertView.findViewById(R.id.img_data);
        TextView tvData = (TextView)convertView.findViewById(R.id.tv_data);

        AndroidModel androidModel = (AndroidModel)getItem(position);

        imgData.setImageResource(androidModel.getImgid());
        tvData.setText(androidModel.getStrpgid());


        return convertView;
    }
}
