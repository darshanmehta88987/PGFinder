package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

class MyReviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<ReviewModel> reviewModelArrayList;

    public MyReviewAdapter(Context context, ArrayList<ReviewModel> reviewModelArrayList) {
        this.context=context;
        this.reviewModelArrayList=reviewModelArrayList;
    }

    @Override
    public int getCount() {
        return reviewModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.layout_review, null);

        TextView tvUsername,tvReview;
        RatingBar ratingReview;

        tvUsername = (TextView)convertView.findViewById(R.id.tv_username);
        tvReview = (TextView)convertView.findViewById(R.id.tv_rev_desc);
        ratingReview = (RatingBar)convertView.findViewById(R.id.rating_review);

        Log.e("Review***","Size is "+reviewModelArrayList.get(position).getName());


        tvUsername.setText(reviewModelArrayList.get(position).getName());
        tvReview.setText(reviewModelArrayList.get(position).getReview());
        ratingReview.setRating(Float.parseFloat(reviewModelArrayList.get(position).getRating()));
        ratingReview.setEnabled(false);

        return convertView;
    }
}
