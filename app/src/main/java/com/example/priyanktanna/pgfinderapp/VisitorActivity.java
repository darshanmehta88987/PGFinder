package com.example.priyanktanna.pgfinderapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

public class VisitorActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference mDataReference;
    ArrayList<String> pg;
    ArrayList<Tenent1Model> tenent1ModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        Intent intent = getIntent();
        final String gender = intent.getStringExtra("GENDER");
        listView = (ListView) findViewById(R.id.listview);
        mDataReference = FirebaseDatabase.getInstance().getReference("Tenent_Register");
        pg = new ArrayList<>();
        tenent1ModelArrayList = new ArrayList<>();
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(VisitorActivity.this, "IM", Toast.LENGTH_SHORT).show();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //pg.add(dataSnapshot1.getKey());
                    Log.e("Before +", "Before +" + dataSnapshot1.getKey());
                    //pg.add(dataSnapshot1.getKey());
                    pg.add(dataSnapshot1.getKey());


                    /*Tenent1Model tenent1Model = dataSnapshot1.getValue(Tenent1Model.class);
                    tenent1ModelArrayList.add(tenent1Model);
*/


                }
                for (int k = 0; k < pg.size(); k++) {
                    Log.e("Before +", "Inside KKK");
                    mDataReference.child(pg.get(k)).addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("Before +", "Inside PG");
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Log.e("Before +", "Inside PG LOOP");

                                Tenent1Model tenent1Model = dataSnapshot1.getValue(Tenent1Model.class);

                                if (gender.equals(tenent1Model.getPgFor())) {
                                    tenent1ModelArrayList.add(tenent1Model);
                                    Log.e("Before +", "length is PGISINSIDE" + tenent1ModelArrayList.size());


                                }


                            }
                            setdata(tenent1ModelArrayList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                Log.e("Before +", "length is PGIS" + tenent1ModelArrayList.size());
                //setdata(tenent1ModelArrayList);
                //Log.e("Before +","PG is "+tenent1ModelArrayList.get(0).getPgName());
                Log.e("Before +", "length is " + pg.size());
                //setdata(tenent1ModelArrayList);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void setdata(ArrayList<Tenent1Model> list) {
        MyVisitorAdapter myVisitorAdapter = new MyVisitorAdapter(VisitorActivity.this, list);
        listView.setAdapter(myVisitorAdapter);
    }

}
