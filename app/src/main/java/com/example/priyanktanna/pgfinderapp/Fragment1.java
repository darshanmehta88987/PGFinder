package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class Fragment1 extends Fragment {

    TextView textView;
    FloatingActionButton btnFloting;
    private SharedPreferences sharedPreferences;
    private String userId;
    private DatabaseReference myRef1;
    private String tId;
    ArrayList<Tenent1Model> tenent1ModelArrayList;
    ListView listView;
    private DatabaseReference myRef3;
    private String timerModel_tID;
    ArrayList<String> pglist;
    private Tenent1Model tenent1Model;
    private int counter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment1, container, false);
        tenent1ModelArrayList = new ArrayList<>();
        listView = (ListView) rootview.findViewById(R.id.list_tenent);

        btnFloting = (FloatingActionButton) rootview.findViewById(R.id.fab);
        btnFloting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), TenentAddPgLayOutFloating.class);
                startActivity(i);

            }
        });

        pglist = new ArrayList<String>();
        sharedPreferences = getActivity().getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("USER_KEY", "");
        Log.e("FLOATING", userId);
        tId = sharedPreferences.getString("USER_KEY", "");
        Log.e("FLOATING", tId);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef1 = database.getReference("Tenent_Register");
        myRef3 = database.getReference("Tenent_Timer");

        myRef1.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    tenent1Model = dataSnapshot1.getValue(Tenent1Model.class);
                    Log.e("FFFFFF", tenent1Model.getPgName());
                    final String pgid = tenent1Model.gettId();


                    myRef3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                TimerModel timerModel = dataSnapshot1.getValue(TimerModel.class);
                                timerModel_tID = timerModel.getTenentId();
                                //  timeDur = timerModel.getTimeSet();
                                //timeDate = timerModel.getStrdatetime();

                                if (timerModel_tID.equals(pgid)) {


                                    String strTime = timerModel.getStrdatetime();
                                    String timedur = timerModel.getTimeSet();
                                    Log.e("time", "**" + strTime);
                                    Log.e("time", "**" + timedur);

                                    setdata(tenent1Model,pgid, strTime, timedur);

                                    // tenent1ModelArrayList.add(tenent1Model);


                                }

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootview;
    }

    private void setdata(Tenent1Model tenent1Model, final String pgid, String strTime, String timedur) {

        String time = strTime;
        int d = Integer.parseInt(timedur);
        String[] data = time.split(" ", 6);
        Log.e("time", "" + data[2]);
        String t = data[3];
        String[] tdata = t.split(":", 3);
        String t1 = tdata[0];
        String t2 = tdata[1];
        String tf = t1 + "." + t2;
        float tfff = Float.parseFloat(tf);
        Log.e("tfff", "" + tfff);

        final int interval = 7; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                Log.e("tfff", "gduihiuhgih");
               // myRef1.child().child(pgid).removeValue();
                Toast.makeText(getActivity(), "C'Mom no hands!", Toast.LENGTH_SHORT).show();
            }
        };

        handler.postAtTime(runnable, (long) (tfff + (2000*60)));
        handler.postDelayed(runnable, 2000*60);

        tenent1ModelArrayList.add(tenent1Model);
        MyTenentAdapter myTenentAdapter = new MyTenentAdapter(getContext(), tenent1ModelArrayList);
        listView.setAdapter(myTenentAdapter);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
