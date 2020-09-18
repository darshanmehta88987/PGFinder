package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MyTenentWishlistAdapter extends BaseAdapter {

    Context context;
    ArrayList<TenentWishlistModel> tenentWishlistModelArrayList;

    public MyTenentWishlistAdapter(Context context, ArrayList<TenentWishlistModel> tenentWishlistModelArrayList) {
        this.context = context;
        this.tenentWishlistModelArrayList=tenentWishlistModelArrayList;
    }

    @Override
    public int getCount() {
        return tenentWishlistModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return tenentWishlistModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.raw_tenent_wishlist, null);

        TextView tvName,tvPhone;
        tvName = (TextView)convertView.findViewById(R.id.tv_name);
        tvPhone = (TextView)convertView.findViewById(R.id.tv_phone);

        tvName.setText(tenentWishlistModelArrayList.get(position).getName());
        tvPhone.setText(tenentWishlistModelArrayList.get(position).getPhone());

        return convertView;
    }
}
