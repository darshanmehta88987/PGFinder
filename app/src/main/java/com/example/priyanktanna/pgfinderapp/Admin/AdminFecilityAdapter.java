package com.example.priyanktanna.pgfinderapp.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.priyanktanna.pgfinderapp.R;

import java.util.ArrayList;

public class AdminFecilityAdapter extends BaseAdapter {


    Context context;
    ArrayList<FacilityInfo> facilityInfoArrayList;
    private String strfact;


    public AdminFecilityAdapter(Context context, ArrayList<FacilityInfo> facilityInfoArrayList) {

        this.context = context;
        this.facilityInfoArrayList = facilityInfoArrayList;

    }

    @Override
    public int getCount() {
        return facilityInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return facilityInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;
        CompleteListViewHolder viewHolder;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.raw_admin_fact_list, null);
            viewHolder = new CompleteListViewHolder(v);
            v.setTag(viewHolder);

        } else {
            viewHolder = (CompleteListViewHolder) v.getTag();
        }
        final FacilityInfo facilityInfo = (FacilityInfo) getItem(position);
        viewHolder.tvName.setText(facilityInfo.getFacilityName());


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ManageFacupdateActivity.class);
                i.putExtra("FAC_ID", facilityInfo.getFacilityId());
                i.putExtra("FAC_NAME", facilityInfo.getFacilityName());

                context.startActivity(i);
                facilityInfoArrayList.clear();
            }
        });

        return v;
    }

    class CompleteListViewHolder {
        public TextView tvName;

        public CompleteListViewHolder(View base) {
            tvName = (TextView) base.findViewById(R.id.tv_fact);


        }

    }
}