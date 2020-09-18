package com.example.priyanktanna.pgfinderapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.List;

public class WishlistFragment extends Fragment {

    TextView textView;
    ListView listView;
    private DatabaseReference mDataReference;
    private DatabaseReference mDataReference1;

    private SharedPreferences sharedPreferences;
    ArrayList<String> pgWish;
    ArrayList<Tenent1Model> tenent1ModelArrayList;
    ArrayList<String> tenentKey;
    int k=0;
    ArrayList<String> pg;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_wishlist, container, false);

        textView = (TextView) rootview.findViewById(R.id.tvWishlist);

        listView = (ListView) rootview.findViewById(R.id.list_wishlist);
        mDataReference = FirebaseDatabase.getInstance().getReference("Wishlist");
        pgWish = new ArrayList<String>();
        tenent1ModelArrayList = new ArrayList<>();
        tenentKey = new ArrayList<>();
        pg = new ArrayList<>();
        mDataReference1 = FirebaseDatabase.getInstance().getReference("Tenent_Register");

        sharedPreferences = this.getActivity().getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);
        final String strKey = sharedPreferences.getString("USER_KEY", "");

        Log.e("Wishlist","Str key "+strKey);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        mDataReference.child(strKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    pgWish.add(key);
                    Log.e("Wishlist", "PGWISH KEY" + snapshot.getKey());
                    setPg(pgWish);
                }

                Log.e("Wishlist", "PGWISH Size is" + pgWish.size());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDataReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(getContext(), "IM", Toast.LENGTH_SHORT).show();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.e("Before +", "Before +" + dataSnapshot1.getKey());
                    pg.add(dataSnapshot1.getKey());

                }
                Log.e("Wishlist ","PG Wish"+pgWish.size());

                Log.e("Wishlist ","PG "+pg.size());
                for (k = 0; k < pg.size(); k++) {

                    mDataReference1.child(pg.get(k)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                Tenent1Model tenent1Model = dataSnapshot1.getValue(Tenent1Model.class);
                                Log.e("Wishlist ","Tenent "+tenent1Model.gettId());

                                for(int j=0;j<pgWish.size();j++)
                                {
                                    Log.e("Wishlist","Inside for tenent");
                                    if(tenent1Model.gettId().equals(pgWish.get(j)))
                                    {
                                        tenent1ModelArrayList.add(tenent1Model);
                                    }
                                }
                                Log.e("Wishlist", "length is PGISINSIDE" + tenent1ModelArrayList.size());

                            }
                            setdata(tenent1ModelArrayList);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                //Log.e("Wishlist", "length is PGIS" + tenent1ModelArrayList.size());
                Log.e("Wishlist", "length is " + pg.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootview;
    }

    private void setPg(ArrayList<String> pgWish) {

    }

    private void setdata(ArrayList<Tenent1Model> tenent1ModelArrayList) {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();

        }
        MyWishlistAdapter myWishlistAdapter = new MyWishlistAdapter(getContext(), tenent1ModelArrayList);
        listView.setAdapter(myWishlistAdapter);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("WISHLIST");
    }
}


/*mDataReference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot2 : dataSnapshot.getChildren())
                        {
                            tenentKey.add(dataSnapshot2.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
*/



                /*for (k = 0; k < pgWish.size(); k++) {

                    mDataReference1.child(tenentKey.get(k)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot3 : dataSnapshot.getChildren())
                            {
                                Tenent1Model tenent1Model = dataSnapshot3.getValue(Tenent1Model.class);
                                if(pgWish.get(k).equals(tenent1Model.gettId()))
                                {
                                    tenent1ModelArrayList.add(tenent1Model);
                                }
                            }
                            setdata(tenent1ModelArrayList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });






                }
*/
