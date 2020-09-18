package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class MyHomeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Tenent1Model> list;

    public MyHomeAdapter(Context context, ArrayList<Tenent1Model> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.raw_userhome, null);

        Tenent1Model tenent1Model = (Tenent1Model)getItem(position);
        ImageView i1;
        i1 = (ImageView) convertView.findViewById(R.id.img_data);
        TextView tv_data;
        TextView tv_bhk;
        TextView tvAdd;

        tv_data = (TextView) convertView.findViewById(R.id.tv_data);
        tv_bhk = (TextView) convertView.findViewById(R.id.tv_bhk);
        tvAdd = (TextView) convertView.findViewById(R.id.tvAdd);

        tv_data.setText(list.get(position).getPgName());
        int single, multi;
        single = Integer.parseInt(list.get(position).getSingleSpin());
        multi = Integer.parseInt(list.get(position).getMultiSpin());
        single = single + multi;
        String str;
        str = String.valueOf(single);
        tv_bhk.setText(str + " bhk");
        //tv_bhk.setText(list.get(position).getMultiSpin()+list.get(position).getSingleSpin());
        tvAdd.setText(list.get(position).getPgAdd());

        ArrayList<String> urldata = list.get(position).getUrl();


        for (int i = 0 ; urldata.size()>i;i++){

            Log.e("0 URL ==", "*****"+urldata.get(0).toString());

            Glide.with(context)
                    .load(urldata.get(0).toString())
                    .into(i1);
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PGActivity.class);
                intent.putExtra("uid",list.get(position).getUid());
                intent.putExtra("tid",list.get(position).gettId());
                intent.putExtra("pgName", list.get(position).getPgName());
                intent.putExtra("pgAdd", list.get(position).getPgAdd());
                intent.putExtra("pgFor", list.get(position).getPgFor());
                intent.putExtra("singleSpin", list.get(position).getSingleSpin());
                intent.putExtra("multiSpin", list.get(position).getMultiSpin());
                intent.putExtra("avgPerSpi", list.get(position).getAvgPerSpi());
                intent.putExtra("multiRent", list.get(position).getMultiRent());
                intent.putExtra("singleRent", list.get(position).getSingleRent());
                intent.putStringArrayListExtra("facilityList", list.get(position).getFacilityList());
                intent.putStringArrayListExtra("url", list.get(position).getUrl());
                context.startActivity(intent);
                //list.clear();

            }
        });
        /*String pgName;
        String pgAdd;
        String pgFor;
        String singleSpin;
        String multiSpin;
        String avgPerSpi;
        String multiRent;
        String singleRent;
        ArrayList<String> facilityList;
        ArrayList<String> url;
*/


        return convertView;
    }
}
