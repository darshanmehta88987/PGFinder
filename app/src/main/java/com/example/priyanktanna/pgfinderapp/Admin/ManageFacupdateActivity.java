package com.example.priyanktanna.pgfinderapp.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.priyanktanna.pgfinderapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageFacupdateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;

    Button btnupdate;
    Button btndelete;


    private FirebaseAuth auth;
    private Context activity;
    private String catid;


    ArrayList<String> facilityInfoArrayList;
   // ArrayList<String> stringArrayList;
    private String facilityId;
    private DatabaseReference mDataReference;
    private String strfact;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_facility);

        editText = (EditText) findViewById(R.id.edt_facilityname);

        btnupdate = (Button) findViewById(R.id.btn_update);
        btnupdate.setOnClickListener(this);

        btndelete = (Button) findViewById(R.id.btn_delete);
        btndelete.setOnClickListener(this);

        facilityInfoArrayList = new ArrayList<String>();

        Intent intent = getIntent();

        facilityId= intent.getStringExtra("FAC_ID");

        String facname = intent.getStringExtra("FAC_NAME");

        editText.setText(facname);

        mDataReference = FirebaseDatabase.getInstance().getReference("Admin_add_facility");

        //fileRef = null;'

        setdata(mDataReference);


    }

    private void setdata(DatabaseReference mDataReference) {

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    FacilityInfo facilityInfo = dataSnapshot1.getValue(FacilityInfo.class);

                  facilityInfoArrayList.add(facilityInfo.facilityName);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_update:

                String facname = editText.getText().toString();

                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();

                FacilityInfo facilityInfo = new FacilityInfo();
                facilityInfo.setFacilityName(facname);
                facilityInfo.setFacilityId(facilityId);
                mDataReference.child(facilityId).setValue(facilityInfo);
                finish();
                //facilityInfo.setFacilityId(facilityId);


                break;


            case R.id.btn_delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();

                mDataReference.child(facilityId).removeValue();
                finish();

                break;

        }
    }




}
