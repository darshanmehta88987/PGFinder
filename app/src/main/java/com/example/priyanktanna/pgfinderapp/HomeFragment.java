package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

public class HomeFragment extends Fragment {


    /* ViewPager viewPager;
     int images[] = {R.drawable.pg_1, R.drawable.pg_2, R.drawable.pg_3, R.drawable.pg_4, R.drawable.pg_5, R.drawable.pg_6};
     MyCustomPagerAdapter myCustomPagerAdapter;
 */
    Button btnSortLowtoHigh;
    ArrayList<Tenent1Model> sortingLowToHighMulti;
    Button btnSortLowtoHighSingle;
    ArrayList<Tenent1Model> sortingLowToHighSingle;

    Button btnSortHightoLow;
    ArrayList<Tenent1Model> sortingtHightoLowMulti;
    Button btnSortHightoLowSingle;
    ArrayList<Tenent1Model> sortingtHightoLowSingle;


    ListView listView;
    private DatabaseReference mDataReference;
    ArrayList<Tenent1Model> tenent1ModelArrayList;
    private SharedPreferences sharedPreferences;
    public ArrayList<String> pg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View rootview = inflater.inflate(R.layout.home_fragment, container, false);
        listView = (ListView) rootview.findViewById(R.id.list_home);
        mDataReference = FirebaseDatabase.getInstance().getReference("Tenent_Register");
        tenent1ModelArrayList = new ArrayList<>();
        pg = new ArrayList<>();
        sharedPreferences = this.getContext().getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);
        final String gender = sharedPreferences.getString("Gender","");
        Log.e("Gender","Gender is "+gender);
        sortingLowToHighMulti = new ArrayList<>();
        btnSortLowtoHigh = (Button) rootview.findViewById(R.id.btn_sort_low_to_high_multi);
        btnSortLowtoHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sortingLowToHighMulti.size()==0)
                {
                    Toast.makeText(getContext(), "No Pg available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setdata(sortingLowToHighMulti);
                }

            }
        });
        sortingLowToHighSingle = new ArrayList<>();
        btnSortLowtoHighSingle = (Button) rootview.findViewById(R.id.btn_sort_low_to_high_single);
        btnSortLowtoHighSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortingLowToHighSingle.size()==0)
                {
                    Toast.makeText(getContext(), "No Pg available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setdata(sortingLowToHighSingle);
                }

            }
        });

        sortingtHightoLowMulti = new ArrayList<>();
        btnSortHightoLow = (Button) rootview.findViewById(R.id.btn_sort_high_to_low_multi);
        btnSortHightoLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortingtHightoLowMulti.size()==0)
                {
                    Toast.makeText(getContext(), "No Pg available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setdata(sortingtHightoLowMulti);
                }

            }
        });
        sortingtHightoLowSingle = new ArrayList<>();
        btnSortHightoLowSingle = (Button) rootview.findViewById(R.id.btn_sort_high_to_low_single);
        btnSortHightoLowSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sortingtHightoLowSingle.size()==0)
                {
                    Toast.makeText(getContext(), "No Pg available", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setdata(sortingtHightoLowSingle);
                }

            }
        });

        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(getContext(), "IM", Toast.LENGTH_SHORT).show();
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

                                if(gender.equals(tenent1Model.getPgFor()))
                                {
                                    tenent1ModelArrayList.add(tenent1Model);
                                    Log.e("Before +", "length is PGISINSIDE" + tenent1ModelArrayList.size());


                                    if (!(Integer.parseInt(tenent1Model.getMultiRent()) == 0)) {
                                        sortingLowToHighMulti.add(tenent1Model);
                                        sortingtHightoLowMulti.add(tenent1Model);
                                    }
                                    if (!(Integer.parseInt(tenent1Model.getSingleRent()) == 0)) {
                                        sortingLowToHighSingle.add(tenent1Model);
                                        sortingtHightoLowSingle.add(tenent1Model);
                                    }

                                }



                            }
                            setdata(tenent1ModelArrayList);
                            sortingLowToHighMulti.sort(new Comparator<Tenent1Model>() {
                                @Override
                                public int compare(Tenent1Model o1, Tenent1Model o2) {
                                    if (o1.getMultiRent() == o2.getMultiRent()) {
                                        return 0;

                                    } else if (Integer.parseInt(o1.getMultiRent()) > (Integer.parseInt(o2.getMultiRent()))) {
                                        return 1;

                                    } else {
                                        return -1;
                                    }

                                }
                            });
                            sortingLowToHighSingle.sort(new Comparator<Tenent1Model>() {
                                @Override
                                public int compare(Tenent1Model o1, Tenent1Model o2) {
                                    if (o1.getSingleRent() == o2.getSingleRent()) {
                                        return 0;

                                    } else if (Integer.parseInt(o1.getSingleRent()) > (Integer.parseInt(o2.getSingleRent()))) {
                                        return 1;

                                    } else {
                                        return -1;
                                    }

                                }
                            });
                            sortingtHightoLowMulti.sort(new Comparator<Tenent1Model>() {
                                @Override
                                public int compare(Tenent1Model o1, Tenent1Model o2) {
                                    if (o1.getMultiRent() == o2.getMultiRent()) {
                                        return 0;

                                    } else if (Integer.parseInt(o1.getMultiRent()) > (Integer.parseInt(o2.getMultiRent()))) {
                                        return -1;

                                    } else {
                                        return 1;
                                    }

                                }
                            });
                            sortingtHightoLowSingle.sort(new Comparator<Tenent1Model>() {
                                @Override
                                public int compare(Tenent1Model o1, Tenent1Model o2) {
                                    if (o1.getSingleRent() == o2.getSingleRent()) {
                                        return 0;

                                    } else if (Integer.parseInt(o1.getSingleRent()) > (Integer.parseInt(o2.getSingleRent()))) {
                                        return -1;

                                    } else {
                                        return 1;
                                    }
                                }
                            });
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


        return rootview;
    }


    private void setdata(ArrayList<Tenent1Model> list) {
        MyHomeAdapter myHomeAdapter = new MyHomeAdapter(getContext(), list);
        listView.setAdapter(myHomeAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("HOME");
    }
}
