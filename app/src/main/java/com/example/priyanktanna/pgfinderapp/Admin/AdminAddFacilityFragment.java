package com.example.priyanktanna.pgfinderapp.Admin;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priyanktanna.pgfinderapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminAddFacilityFragment extends Fragment implements View.OnClickListener {

    TextView textView;
    EditText editText;
    Button btnaddfacility;
    private DatabaseReference mDataReference;
    private DatabaseReference myRef;
    private DatabaseReference mDataReference1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.adminfragmentaddfacility, container, false);

        textView = (TextView) rootview.findViewById(R.id.tvaddfacility);

        editText = (EditText) rootview.findViewById(R.id.edt_addfacility);

        btnaddfacility = (Button) rootview.findViewById(R.id.btn_addfacility);
        btnaddfacility.setOnClickListener(this);

        mDataReference = FirebaseDatabase.getInstance().getReference("Admin_add_facility");

        myRef = FirebaseDatabase.getInstance().getReference("Admin");

        setData(mDataReference);


        return rootview;
    }

    private void setData(DatabaseReference mDataReference) {

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    FacilityInfo facilityInfo = dataSnapshot1.getValue(FacilityInfo.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("ADD FACILITY");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_addfacility:

                String straddfacility = editText.getText().toString();

                String key = mDataReference.push().getKey();
                FacilityInfo facilityInfo = new FacilityInfo();

                facilityInfo.setFacilityId(key);
                facilityInfo.setFacilityName(straddfacility);
                mDataReference.child(key).setValue(facilityInfo);
                Toast.makeText(getActivity(), "Facility added", Toast.LENGTH_SHORT).show();

                break;

        }
    }
}
