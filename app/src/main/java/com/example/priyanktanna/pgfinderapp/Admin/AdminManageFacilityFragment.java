package com.example.priyanktanna.pgfinderapp.Admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.priyanktanna.pgfinderapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminManageFacilityFragment extends Fragment {


    TextView textView;
    ListView listView;
    ArrayList<FacilityInfo> facilityInfoArrayList;
    private DatabaseReference mDataReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.adminfragmentmanagefacility,container,false);

        textView=(TextView)rootview.findViewById(R.id.tvmanagefacility);

        listView=(ListView)rootview.findViewById(R.id.listFacility);

        mDataReference = FirebaseDatabase.getInstance().getReference("Admin_add_facility");

        facilityInfoArrayList = new ArrayList<FacilityInfo>();
        setData(mDataReference);

        return rootview;
    }

    private void setData(DatabaseReference mDataReference) {
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    FacilityInfo facilityInfo = dataSnapshot1.getValue(FacilityInfo.class);
                    Log.e("MANAGE", ""+facilityInfo.getFacilityName());

                    facilityInfoArrayList.add(facilityInfo);
                    setdataAdapter();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setdataAdapter() {


        AdminFecilityAdapter adminFecilityAdapter =  new AdminFecilityAdapter(getActivity() , facilityInfoArrayList);
        listView.setAdapter(adminFecilityAdapter);
        textView.setVisibility(View.GONE);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("MANAGE FACILITY");
    }
}
