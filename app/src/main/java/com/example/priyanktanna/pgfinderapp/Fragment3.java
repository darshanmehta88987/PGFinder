package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import java.util.Comparator;
import java.util.List;
import java.util.zip.DataFormatException;

public class Fragment3 extends Fragment {

    ListView listView;
    DatabaseReference myref;
    DatabaseReference myref1;


    ArrayList<String> wUserIds;
    ArrayList<String> wTenentIds;
    ArrayList<String> tTenentIds;
    ArrayList<String> w1;
    ArrayList<String> w2;
    ArrayList<String> w3;



    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment3, container, false);
        listView = (ListView)rootview.findViewById(R.id.list_tenent_wishlist);
        myref = FirebaseDatabase.getInstance().getReference("Tenent_Register");
        myref1 = FirebaseDatabase.getInstance().getReference("Wishlist");

        sharedPreferences = this.getContext().getSharedPreferences("PGFINDER",Context.MODE_PRIVATE);

        wUserIds = new ArrayList<>();
        wTenentIds = new ArrayList<>();
        tTenentIds = new ArrayList<>();
        w1 = new ArrayList<>();
        w2 = new ArrayList<>();
        w3 = new ArrayList<>();




        final String strTenentKey;
        strTenentKey = sharedPreferences.getString("USER_KEY","");



        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot  : dataSnapshot.getChildren())
                {
                    w1.add(snapshot.getKey());
                }
                for(int i=0;i<w1.size();i++)
                {
                    myref1.child(w1.get(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                w2.add(snapshot.getKey());
                                Log.e("W2","KEY "+snapshot.getKey());
                            }
                            Log.e("W2SIZES","W2 size is "+w2.size());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                Log.e("W2SIZE","W2 size is "+w2.size());
                for(int i=0;i<w2.size();i++)
                {
                    myref1.child(w1.get(i)).child(w2.get(i)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                WishlistModel wishlistModel = snapshot.getValue(WishlistModel.class);
                                Log.e("USERIDIS","User id is "+wishlistModel.getUid());
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





        myref.child(strTenentKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Tenent1Model tenent1Model = dataSnapshot1.getValue(Tenent1Model.class);
                    Log.e("PGNAME","PG NAME "+tenent1Model.getPgName());
                    tTenentIds.add(tenent1Model.gettId());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return rootview;
    }

    private void setWtid(ArrayList<String> wTenentIds) {
        this.wTenentIds=wTenentIds;
    }
}